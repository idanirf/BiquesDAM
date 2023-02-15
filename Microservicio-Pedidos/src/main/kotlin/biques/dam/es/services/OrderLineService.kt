package biques.dam.es.services

import biques.dam.es.models.OrderLine
import biques.dam.es.repositories.orderline.OrderLineRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class OrderLineService(
    val orderLineRepository: OrderLineRepository,
) {
    fun getAllOrderLine(): Flow<OrderLine> {
        return orderLineRepository.findAll()
    }

    suspend fun getOrderLineByUUID(uuid: UUID): OrderLine {
        return orderLineRepository.findByUUID(uuid)
    }

    suspend fun saveOrderLine(orderLine: OrderLine): OrderLine {
        return orderLineRepository.save(orderLine)
    }

    suspend fun updateOrderLine(orderLine: OrderLine): OrderLine {
        return orderLineRepository.update(orderLine)
    }

    suspend fun deleteOrderLine(orderLine: OrderLine): Boolean {
        return orderLineRepository.delete(orderLine)
    }

}
