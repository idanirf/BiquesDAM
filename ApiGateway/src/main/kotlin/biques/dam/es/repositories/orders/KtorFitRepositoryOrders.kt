package biques.dam.es.repositories.orders

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderDTOUpdate
import biques.dam.es.dto.OrderSaveDTO
import biques.dam.es.exceptions.AppointmentConflictIntegrityException
import biques.dam.es.exceptions.AppointmentNotFoundException
import biques.dam.es.exceptions.OrderErrorException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.services.orders.KtorFitClientOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*
/**
 * Implementation of the IOrdersRepository interface.
 * @author BiquesDAM-Team
 */
@Single
@Named("KtorFitRepositoryOrders")
class KtorFitRepositoryOrders : IOrdersRepository {
    private val client by lazy { KtorFitClientOrders.instance }

    /**
     * Returns a flow of all orders.
     * @param token the access token for authentication.
     * @return a flow of all orders in the database.
     * @throws OrderNotFoundException if the orders are not found.
     */
    override suspend fun findAll(token: String): List<OrderDTO> = withContext(Dispatchers.IO) {
        val call = async {
            client.getAllOrder(token).data
        }
        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw OrderNotFoundException("Error. Orders not found")
        }
    }

    /**
     * Finds an order by its ID.
     * @param token the access token for authentication.
     * @param id the ID of the order to find.
     * @return the order object
     * @throws OrderNotFoundException if the order is not found.
     */
    override suspend fun findById(token: String, id: UUID): OrderDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.getByIdOrder(token, id.toString())
        }
        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw OrderNotFoundException("The order with id $id does not exist")
        }
    }

    /**
     * Saves a new order.
     * @param token the access token for authentication.
     * @param entity the new order to save.
     * @return the saved order object.
     * @throws OrderErrorException if the order cannot be saved.
     */
    override suspend fun save(token: String, entity: OrderSaveDTO): OrderDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.createOrder(token, entity)
        }
        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw OrderErrorException("Error creating order: ${e.message}")
        }
    }

    /**
     * Updates an existing order with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the order to update.
     * @param entity the new order data to update.
     * @return the updated order object.
     * @throws OrderNotFoundException if the order to update is not found.
     */
    override suspend fun update(token: String, id: UUID, entity: OrderDTOUpdate): OrderDTO =
        withContext(Dispatchers.IO) {
            val call = async {
                client.updateOrder(token, id.toString(), entity)
            }
            try {
                return@withContext call.await()
            } catch (e: Exception) {
                throw OrderNotFoundException("The order with id $id does not exist")
            }
        }

    /**
     * Delete an order with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the order to delete.
     * @throws OrderNotFoundException if the order to delete is not found.
     */
    override suspend fun delete(token: String, id: UUID) = withContext(Dispatchers.IO) {
        val call = async {
            client.deleteOrder(token, id.toString())
        }
        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw OrderNotFoundException("The order with id $id does not exist")
        }
    }

}