package inoxoft.simon.myduka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import inoxoft.simon.myduka.data.repository.FinanceRepository
import inoxoft.simon.myduka.screens.FinanceRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FinanceViewModel(private val repository: FinanceRepository) : ViewModel() {
    val records: StateFlow<List<FinanceRecord>> = repository.allRecords
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addRecord(cash: Float, float: Float, workingAmount: Float) {
        viewModelScope.launch {
            val record = FinanceRecord(
                id = 0,
                date = System.currentTimeMillis(),
                cash = cash,
                float = float,
                workingAmount = workingAmount
            )
            repository.addRecord(record)
        }
    }

    fun deleteRecord(record: FinanceRecord) {
        viewModelScope.launch {
            repository.deleteRecord(record)
        }
    }

    class Factory(private val repository: FinanceRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FinanceViewModel(repository) as T
        }
    }
} 