package inoxoft.simon.myduka.data.dao

import androidx.room.*
import inoxoft.simon.myduka.data.entities.FinanceRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FinanceRecordDao {
    @Query("SELECT * FROM finance_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<FinanceRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: FinanceRecordEntity)

    @Query("SELECT * FROM finance_records WHERE date >= :startDate ORDER BY date DESC")
    fun getRecordsAfterDate(startDate: Long): Flow<List<FinanceRecordEntity>>

    @Delete
    suspend fun deleteRecord(record: FinanceRecordEntity)
} 