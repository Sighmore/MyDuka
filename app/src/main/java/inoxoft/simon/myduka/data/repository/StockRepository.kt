package inoxoft.simon.myduka.data.repository

import inoxoft.simon.myduka.data.dao.StockDao
import inoxoft.simon.myduka.data.entities.StockEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import inoxoft.simon.myduka.screens.StockItem

class StockRepository(private val stockDao: StockDao) {
    val allStock: Flow<List<StockItem>> = stockDao.getAllStock()
        .map { entities ->
            entities.map { entity ->
                StockItem(
                    id = entity.id,
                    name = entity.name,
                    quantity = entity.quantity,
                    buyingPrice = entity.buyingPrice,
                    sellingPrice = entity.sellingPrice,
                    reorderPoint = entity.reorderPoint
                )
            }
        }

    suspend fun addStock(stock: StockItem) {
        stockDao.insertStock(stock.toEntity())
    }

    suspend fun updateStock(stock: StockItem) {
        stockDao.updateStock(stock.toEntity())
    }

    suspend fun deleteStock(stock: StockItem) {
        stockDao.deleteStock(stock.toEntity())
    }

    private fun StockItem.toEntity() = StockEntity(
        id = id,
        name = name,
        quantity = quantity,
        buyingPrice = buyingPrice,
        sellingPrice = sellingPrice,
        reorderPoint = reorderPoint
    )
} 