package biques.dam.es.repositories.orders

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderDTOCreate
import biques.dam.es.dto.OrderDTOUpdate
import biques.dam.es.exceptions.OrderErrorException
import biques.dam.es.exceptions.OrderException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.services.orders.KtorFitClientOrders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.UUID

@Single
@Named("KtorFitRepositoryOrders")
class KtorFitRepositoryOrders: IOrdersRepository {
    private val client by lazy { KtorFitClientOrders.instance }


    override suspend fun findAll(token: String): Flow<OrderDTO> {
        try {
            return client.getAllOrder(token).asFlow()
        }catch (e: Exception){
            throw OrderNotFoundException("Error. Orders not found")
        }
    }

    override suspend fun findById(token: String, id: UUID): OrderDTO {
        try{
            return client.getByIdOrder(token, id.toString())
        }catch (e: Exception){
            throw OrderNotFoundException("The order with id $id does not exist")
        }
    }

    override suspend fun save(token: String, entity: OrderDTOCreate): OrderDTO {
       try{
           return client.createOrder(token, entity)
       }catch (e: Exception){
           throw OrderErrorException("Error creating order")
       }
    }

    override suspend fun update(token: String, id: UUID, entity: OrderDTOUpdate): OrderDTO {
        try{
            return client.updateOrder(token, id.toString(), entity)
        }catch (e: Exception){
            throw OrderNotFoundException("The order with id $id does not exist")
        }
    }

    override suspend fun delete(token: String, id: UUID) {
        try{
            return client.deleteOrder(token, id.toString())
        }catch (e: Exception){
            throw OrderNotFoundException("The order with id $id does not exist")
        }
    }

}