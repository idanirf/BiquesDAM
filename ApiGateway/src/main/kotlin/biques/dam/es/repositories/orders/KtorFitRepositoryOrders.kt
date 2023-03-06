package biques.dam.es.repositories.orders

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderDTOUpdate
import biques.dam.es.dto.OrderSaveDTO
import biques.dam.es.exceptions.OrderErrorException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.services.orders.KtorFitClientOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("KtorFitRepositoryOrders")
class KtorFitRepositoryOrders : IOrdersRepository {
    private val client by lazy { KtorFitClientOrders.instance }


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