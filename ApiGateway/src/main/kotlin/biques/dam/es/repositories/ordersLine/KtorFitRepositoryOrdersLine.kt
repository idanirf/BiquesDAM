package biques.dam.es.repositories.ordersLine

import biques.dam.es.dto.*
import biques.dam.es.exceptions.*
import biques.dam.es.services.orders.KtorFitClientOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.UUID

/**
 * Implementation of the IOrdersLinneRepository interface.
 * @author BiquesDAM-Team
 */
@Single
@Named("KtorFitRepositoryOrdersLine")
class KtorFitRepositoryOrdersLine: IOrdersLineRepository {
    private val client by lazy { KtorFitClientOrders.instance }

    /**
     * Returns a flow of all orders lines.
     * @param token the access token for authentication.
     * @return a flow of all orders lines in the database.
     * @throws OrderLineNotFoundException if the orders lines are not found.
     */

    override suspend fun findAll(token: String): Flow<OrderLineDTO> = withContext(Dispatchers.IO) {
        val call = async {
            client.getAllOrderLine(token).asFlow()
        }

        try {
            return@withContext call.await()
        }catch (e: Exception){
            throw OrderLineNotFoundException("Error. Orders not found")
        }
    }

    /**
     * Finds an order line by its ID.
     * @param token the access token for authentication.
     * @param id the ID of the order line to find.
     * @return the order line object
     * @throws OrderLineNotFoundException if the order line is not found.
     */
    override suspend fun findById(token: String, id: UUID): OrderLineDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.getByIdOrderLine(token, id.toString())
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw OrderLineNotFoundException("The order with id $id does not exist")
        }
    }

    /**
     * Saves a new order line.
     * @param token the access token for authentication.
     * @param entity the new order line to save.
     * @return the saved order line object.
     * @throws OrderLineNotFoundException if the order line cannot be saved.
     */
    override suspend fun save(token: String, entity: OrderLineCreateDTO): OrderLineDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.createOrderLine(token, entity)
        }

        try {
           call.await()
       } catch (e: Exception) {
           throw OrderLineErrorException("Error creating orderLine")
       }
    }

    /**
     * Updates an existing order line with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the order line to update.
     * @param entity the new order line data to update.
     * @return the updated order line object.
     * @throws OrderLineNotFoundException if the order line to update is not found.
     */
    override suspend fun update(token: String, id: UUID, entity: OrderLineUpdateDTO): OrderLineDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.updateOrderLine(token, id.toString(), entity)
        }

        try{
            return@withContext call.await()
        }catch (e: Exception){
            throw OrderLineNotFoundException("The order with id $id does not exist")
        }
    }

    /**
     * Deletes an order line with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the order line to delete.
     * @throws OrderLineNotFoundException if the order line to delete is not found.
     */
    override suspend fun delete(token: String, id: UUID) = withContext(Dispatchers.IO) {
        val call = async {
            client.deleteOrderLine(token, id.toString())
        }

        try{
            return@withContext call.await()
        }catch (e: Exception){
            throw OrderLineNotFoundException("The order with id $id does not exist")
        }
    }
}