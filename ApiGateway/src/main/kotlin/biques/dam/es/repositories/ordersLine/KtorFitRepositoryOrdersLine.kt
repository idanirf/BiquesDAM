package biques.dam.es.repositories.ordersLine

import biques.dam.es.dto.*
import biques.dam.es.exceptions.*
import biques.dam.es.services.orders.KtorFitClientOrders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.UUID

@Single
@Named("KtorFitRepositoryOrdersLine")
class KtorFitRepositoryOrdersLine: IOrdersLineRepository {
    private val client by lazy { KtorFitClientOrders.instance }


    override suspend fun findAll(token: String): Flow<OrderLineDTO> {
        try {
            return client.getAllOrderLine(token).asFlow()
        }catch (e: Exception){
            throw OrderLineNotFoundException("Error. Orders not found")
        }
    }

    override suspend fun findById(token: String, id: UUID): OrderLineDTO {
        try{
            return client.getByIdOrderLine(token, id.toString())
        }catch (e: Exception){
            throw OrderLineNotFoundException("The order with id $id does not exist")
        }
    }

    override fun save(token: String, entity: OrderLineCreateDTO): OrderLineDTO {
       try{
           return client.createOrderLine(token, entity)
       }catch (e: Exception){
           throw OrderLineErrorException("Error creating orderLine")
       }
    }

    override suspend fun update(token: String, id: UUID, entity: OrderLineUpdateDTO): OrderLineDTO {
        try{
            return client.updateOrderLine(token, id.toString(), entity)
        }catch (e: Exception){
            throw OrderLineNotFoundException("The order with id $id does not exist")
        }
    }

    override suspend fun delete(token: String, id: UUID) {
        try{
            return client.deleteOrderLine(token, id.toString())
        }catch (e: Exception){
            throw OrderLineNotFoundException("The order with id $id does not exist")
        }
    }

}