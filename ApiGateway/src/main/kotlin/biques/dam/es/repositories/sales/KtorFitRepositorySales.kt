package biques.dam.es.repositories.sales

import biques.dam.es.dto.SaleCreateDTO
import biques.dam.es.dto.SaleDTO
import biques.dam.es.exceptions.SaleConflictIntegrityException
import biques.dam.es.exceptions.SaleNotFoundException
import biques.dam.es.services.sales.KtorFitClientSales
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("KtorFitRepositorySales")
class KtorFitRepositorySales : ISalesRepository {

    private val client by lazy { KtorFitClientSales.instance }

    override suspend fun findAll(token: String): List<SaleDTO> = withContext(Dispatchers.IO) {
        val call = async {
            client.getAll(token).data
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw SaleNotFoundException("Error getting sales: ${e.message}")
        }
    }

    override suspend fun delete(token: String, id: UUID) = withContext(Dispatchers.IO) {
        val call = async {
            client.delete(token, id.toString())
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw SaleNotFoundException("Error deleting sale with id $id : ${e.message}")
        }
    }

    override suspend fun update(token: String, id: UUID, entity: SaleCreateDTO): SaleDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.update(token, id.toString(), entity)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw SaleNotFoundException("Error updating sale with id $id : ${e.message}")
        }
    }

    override suspend fun save(token: String, entity: SaleCreateDTO): SaleDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.create(token, entity)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw SaleConflictIntegrityException("Error saving sale $entity : ${e.message}")
        }
    }

    override suspend fun findById(token: String, id: String): List<SaleDTO> = withContext(Dispatchers.IO) {
        val call = async {
            client.getById(token, id).data
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw SaleNotFoundException("Error getting sale with id $id : ${e.message}")
        }
    }
}