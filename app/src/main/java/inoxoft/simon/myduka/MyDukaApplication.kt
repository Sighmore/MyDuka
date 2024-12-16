package inoxoft.simon.myduka

import android.app.Application
import inoxoft.simon.myduka.data.MyDukaDatabase
import inoxoft.simon.myduka.data.repository.CreditRepository
import inoxoft.simon.myduka.data.repository.FinanceRepository
import inoxoft.simon.myduka.data.repository.StockRepository

class MyDukaApplication : Application() {
    private val database by lazy { MyDukaDatabase.getDatabase(this) }
    val creditRepository by lazy { CreditRepository(database.creditCustomerDao()) }
    val financeRepository by lazy { FinanceRepository(database.financeRecordDao()) }
    val stockRepository by lazy { StockRepository(database.stockDao()) }
} 