package biques.dam.es.repositories.orders

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderDTOUpdate
import biques.dam.es.dto.OrderSaveDTO
import java.util.*

interface IOrdersRepository {
    suspend fun findAll(token: String): List<OrderDTO>
    suspend fun findById(token: String, id: UUID): OrderDTO
    suspend fun save(token: String, entity: OrderSaveDTO): OrderDTO
    suspend fun update(token: String, id: UUID, entity: OrderDTOUpdate): OrderDTO
    suspend fun delete(token: String, id: UUID)
}