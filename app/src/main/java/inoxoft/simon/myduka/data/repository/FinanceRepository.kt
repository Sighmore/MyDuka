package inoxoft.simon.myduka.data.repository

import inoxoft.simon.myduka.data.dao.FinanceRecordDao
import inoxoft.simon.myduka.data.entities.FinanceRecordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import inoxoft.simon.myduka.screens.FinanceRecord

class FinanceRepository(private val financeRecordDao: FinanceRecordDao) {
    val allRecords: Flow<List<FinanceRecord>> = financeRecordDao.getAllRecords()
        .map { entities ->
            entities.map { entity ->
                FinanceRecord(
                    id = entity.id,
                    date = entity.date,
                    cash = entity.cash,
                    float = entity.floatAmount,
                    workingAmount = entity.workingAmount
                )
            }
        }

    suspend fun addRecord(record: FinanceRecord) {
        financeRecordDao.insertRecord(
            FinanceRecordEntity(
                date = record.date,
                cash = record.cash,
                floatAmount = record.float,
                workingAmount = record.workingAmount
            )
        )
    }

    suspend fun deleteRecord(record: FinanceRecord) {
        financeRecordDao.deleteRecord(
            FinanceRecordEntity(
                id = record.id,
                date = record.date,
                cash = record.cash,
                floatAmount = record.float,
                workingAmount = record.workingAmount
            )
        )
    }
} 