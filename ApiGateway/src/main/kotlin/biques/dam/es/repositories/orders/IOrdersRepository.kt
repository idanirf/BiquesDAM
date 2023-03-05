package biques.dam.es.repositories.orders

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderDTOCreate
import biques.dam.es.dto.OrderDTOUpdate
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface IOrdersRepository {
    suspend fun findAll(token: String): Flow<OrderDTO>
    suspend fun findById(token: String, id: UUID): OrderDTO
    suspend fun save(token: String, entity: OrderDTOCreate): OrderDTO
    suspend fun update(token: String, id: UUID, entity: OrderDTOUpdate): OrderDTO
    suspend fun delete(token: String, id: UUID)
}