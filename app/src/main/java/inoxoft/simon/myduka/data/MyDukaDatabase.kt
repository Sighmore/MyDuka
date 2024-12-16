package inoxoft.simon.myduka.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import inoxoft.simon.myduka.data.dao.CreditCustomerDao
import inoxoft.simon.myduka.data.dao.FinanceRecordDao
import inoxoft.simon.myduka.data.dao.StockDao
import inoxoft.simon.myduka.data.entities.CreditCustomerEntity
import inoxoft.simon.myduka.data.entities.FinanceRecordEntity
import inoxoft.simon.myduka.data.entities.StockEntity

@Database(
    entities = [
        StockEntity::class,
        CreditCustomerEntity::class,
        FinanceRecordEntity::class
    ],
    version = 1
)
abstract class MyDukaDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
    abstract fun creditCustomerDao(): CreditCustomerDao
    abstract fun financeRecordDao(): FinanceRecordDao

    companion object {
        @Volatile
        private var INSTANCE: MyDukaDatabase? = null

        fun getDatabase(context: Context): MyDukaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDukaDatabase::class.java,
                    "myduka_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 