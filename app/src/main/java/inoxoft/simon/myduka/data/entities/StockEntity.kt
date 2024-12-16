package inoxoft.simon.myduka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_items")
data class StockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val quantity: Int,
    val buyingPrice: Float,
    val sellingPrice: Float,
    val reorderPoint: Int
) 