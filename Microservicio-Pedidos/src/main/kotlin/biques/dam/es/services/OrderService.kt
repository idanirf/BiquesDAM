package biques.dam.es.services

import biques.dam.es.models.Order
import biques.dam.es.repositories.order.OrderRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class OrderService(
    val orderRepository: OrderRepository
) {
    fun getAllOrder(): Flow<Order> {
        return orderRepository.findAll()
    }

    suspend fun getOrderByUUID(uuid: UUID): Order {
        return orderRepository.findByUUID(uuid)
    }

    suspend fun saveOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    suspend fun updateOrder(order: Order): Order {
        return orderRepository.update(order)
    }

    suspend fun deleteOrder(order: Order): Boolean {
        return orderRepository.delete(order)
    }

}
