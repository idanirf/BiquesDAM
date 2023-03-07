package biques.dam.es.repositories.sales

import biques.dam.es.dto.SaleCreateDTO
import biques.dam.es.dto.SaleDTO
import biques.dam.es.exceptions.OrderErrorException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.exceptions.SaleConflictIntegrityException
import biques.dam.es.exceptions.SaleNotFoundException
import biques.dam.es.services.sales.KtorFitClientSales
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*
/**
 * Implementation of the ISalesRepository interface.
 * @author BiquesDAM-Team
 */
@Single
@Named("KtorFitRepositorySales")
class KtorFitRepositorySales : ISalesRepository {

    private val client by lazy { KtorFitClientSales.instance }

    /**
     * Returns a List of all sales.
     * @param token the access token for authentication.
     * @return a list of all sales in the database.
     * @throws SaleNotFoundException if the sales are not found.
     */
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

    /**
     * Delete a sale with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the sale to delete.
     * @throws SaleNotFoundException if the sale to delete is not found.
     */
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

    /**
     * Updates an existing sale with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the sale to update.
     * @param entity the new sale data to update.
     * @return the updated sale object.
     * @throws SaleNotFoundException if the sale to update is not found.
     */
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


    /**
     * Saves a new sale.
     * @param token the access token for authentication.
     * @param entity the new sale to save.
     * @return the saved sale object.
     * @throws SaleConflictIntegrityException if the sale cannot be saved.
     */
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

    /**
     * Finds a sale by its ID.
     * @param token the access token for authentication.
     * @param id the ID of the sale to find.
     * @return the sale object
     * @throws SaleNotFoundException if the sale is not found.
     */
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