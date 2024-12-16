package inoxoft.simon.myduka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credit_customers")
data class CreditCustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val phone: String,
    val creditAmount: Float,
    val isBlacklisted: Boolean
) 