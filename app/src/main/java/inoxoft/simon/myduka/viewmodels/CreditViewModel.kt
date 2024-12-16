package inoxoft.simon.myduka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import inoxoft.simon.myduka.data.repository.CreditRepository
import inoxoft.simon.myduka.screens.CreditCustomer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreditViewModel(private val repository: CreditRepository) : ViewModel() {
    val customers: StateFlow<List<CreditCustomer>> = repository.allCustomers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addCustomer(customer: CreditCustomer) {
        viewModelScope.launch {
            repository.addCustomer(customer)
        }
    }

    fun updateCustomer(customer: CreditCustomer) {
        viewModelScope.launch {
            repository.updateCustomer(customer)
        }
    }

    fun deleteCustomer(customer: CreditCustomer) {
        viewModelScope.launch {
            repository.deleteCustomer(customer)
        }
    }

    class Factory(private val repository: CreditRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreditViewModel(repository) as T
        }
    }
} 