package inoxoft.simon.myduka.data.dao

import androidx.room.*
import inoxoft.simon.myduka.data.entities.StockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_items")
    fun getAllStock(): Flow<List<StockEntity>>

    @Query("SELECT * FROM stock_items WHERE quantity <= reorderPoint")
    fun getLowStockItems(): Flow<List<StockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockEntity)

    @Update
    suspend fun updateStock(stock: StockEntity)

    @Delete
    suspend fun deleteStock(stock: StockEntity)
} 