package inoxoft.simon.myduka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import inoxoft.simon.myduka.data.repository.StockRepository
import inoxoft.simon.myduka.screens.StockItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {
    val stockItems: StateFlow<List<StockItem>> = repository.allStock
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addStock(stock: StockItem) {
        viewModelScope.launch {
            repository.addStock(stock)
        }
    }

    fun updateStock(stock: StockItem) {
        viewModelScope.launch {
            repository.updateStock(stock)
        }
    }

    fun deleteStock(stock: StockItem) {
        viewModelScope.launch {
            repository.deleteStock(stock)
        }
    }

    class Factory(private val repository: StockRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StockViewModel(repository) as T
        }
    }
} 