package inoxoft.simon.myduka.data.dao

import androidx.room.*
import inoxoft.simon.myduka.data.entities.CreditCustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCustomerDao {
    @Query("SELECT * FROM credit_customers")
    fun getAllCustomers(): Flow<List<CreditCustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CreditCustomerEntity)

    @Update
    suspend fun updateCustomer(customer: CreditCustomerEntity)

    @Delete
    suspend fun deleteCustomer(customer: CreditCustomerEntity)
} 