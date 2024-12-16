package inoxoft.simon.myduka.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import inoxoft.simon.myduka.data.repository.CreditRepository
import inoxoft.simon.myduka.data.repository.FinanceRepository
import inoxoft.simon.myduka.data.repository.StockRepository
import kotlinx.coroutines.flow.*
import java.util.*

class DashboardViewModel(
    private val financeRepository: FinanceRepository,
    private val creditRepository: CreditRepository,
    private val stockRepository: StockRepository
) : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val startOfDay = calendar.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    val latestFinanceRecord = financeRepository.allRecords
        .map { records -> records.maxByOrNull { it.date } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val totalCredit = creditRepository.allCustomers
        .map { customers -> customers.sumOf { it.creditAmount.toDouble() }.toFloat() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    val lastThreeDaysRecords = financeRepository.allRecords
        .map { records ->
            records
                .filter { it.date >= startOfDay - (3 * 24 * 60 * 60 * 1000) }
                .sortedByDescending { it.date }
                .take(3)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val lowStockItems = stockRepository.allStock
        .map { items -> items.filter { it.quantity <= it.reorderPoint } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    class Factory(
        private val financeRepository: FinanceRepository,
        private val creditRepository: CreditRepository,
        private val stockRepository: StockRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(financeRepository, creditRepository, stockRepository) as T
        }
    }
} 