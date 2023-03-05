package biques.dam.es.repositories.sales

import biques.dam.es.dto.SaleDTO
import biques.dam.es.exceptions.SaleConflictIntegrityException
import biques.dam.es.exceptions.SaleNotFoundException
import biques.dam.es.services.sales.KtorFitClientSales
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("KtorFitRepositorySales")
class KtorFitRepositorySales : ISalesRepository<SaleDTO, UUID> {

    private val client by lazy { KtorFitClientSales.instance }

    override suspend fun findAll(token: String): Flow<SaleDTO> = withContext(Dispatchers.IO) {
        try {
            return@withContext client.getAll(token).asFlow()
        } catch (e: Exception) {
            throw SaleNotFoundException("Error getting sales: ${e.message}")
        }
    }

    override suspend fun delete(token: String, id: UUID) {
        try {
            client.delete(token, id.toString())
        } catch (e: Exception) {
            throw SaleNotFoundException("Error deleting sale with id $id : ${e.message}")
        }
    }

    override suspend fun update(token: String, id: UUID, entity: SaleDTO): SaleDTO {
        try {
            return client.update(token, id.toString(), entity)
        } catch (e: Exception) {
            throw SaleNotFoundException("Error updating sale with id $id : ${e.message}")
        }
    }

    override suspend fun save(token: String, entity: SaleDTO): SaleDTO {
        try {
           return client.create(token, entity)
        } catch (e: Exception) {
            throw SaleConflictIntegrityException("Error saving sale $entity : ${e.message}")
        }
    }

    override suspend fun findById(token: String, id: UUID): SaleDTO {
        try {
            return client.getById(token, id.toString())
        } catch (e: Exception) {
            throw SaleNotFoundException("Error getting sale with id $id : ${e.message}")
        }
    }
}