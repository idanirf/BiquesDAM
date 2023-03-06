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

@Single
@Named("KtorFitRepositoryOrdersLine")
class KtorFitRepositoryOrdersLine: IOrdersLineRepository {
    private val client by lazy { KtorFitClientOrders.instance }


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