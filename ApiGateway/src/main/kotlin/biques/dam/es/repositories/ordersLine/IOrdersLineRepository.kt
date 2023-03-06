package biques.dam.es.repositories.ordersLine

import biques.dam.es.dto.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface IOrdersLineRepository {
    suspend fun findAll(token: String): Flow<OrderLineDTO>
    suspend fun findById(token: String, id: UUID): OrderLineDTO
    fun save(token: String, entity: OrderLineCreateDTO): OrderLineDTO
    suspend fun update(token: String, id: UUID, entity: OrderLineUpdateDTO): OrderLineDTO
    suspend fun delete(token: String, id: UUID)
}