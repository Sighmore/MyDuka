package inoxoft.simon.myduka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance_records")
data class FinanceRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long = 0,
    val cash: Float = 0f,
    val floatAmount: Float = 0f,
    val workingAmount: Float = 0f
) 