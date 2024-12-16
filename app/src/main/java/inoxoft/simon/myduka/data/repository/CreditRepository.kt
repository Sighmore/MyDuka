package inoxoft.simon.myduka.data.repository

import inoxoft.simon.myduka.data.dao.CreditCustomerDao
import inoxoft.simon.myduka.data.entities.CreditCustomerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import inoxoft.simon.myduka.screens.CreditCustomer

class CreditRepository(private val creditCustomerDao: CreditCustomerDao) {
    val allCustomers: Flow<List<CreditCustomer>> = creditCustomerDao.getAllCustomers()
        .map { entities ->
            entities.map { entity ->
                CreditCustomer(
                    id = entity.id,
                    name = entity.name,
                    phone = entity.phone,
                    creditAmount = entity.creditAmount,
                    isBlacklisted = entity.isBlacklisted
                )
            }
        }

    suspend fun addCustomer(customer: CreditCustomer) {
        creditCustomerDao.insertCustomer(customer.toEntity())
    }

    suspend fun updateCustomer(customer: CreditCustomer) {
        creditCustomerDao.updateCustomer(customer.toEntity())
    }

    suspend fun deleteCustomer(customer: CreditCustomer) {
        creditCustomerDao.deleteCustomer(customer.toEntity())
    }

    private fun CreditCustomer.toEntity() = CreditCustomerEntity(
        id = id,
        name = name,
        phone = phone,
        creditAmount = creditAmount,
        isBlacklisted = isBlacklisted
    )
} 