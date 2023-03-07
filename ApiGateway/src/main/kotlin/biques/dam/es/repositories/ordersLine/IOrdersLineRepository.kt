package biques.dam.es.repositories.ordersLine

import biques.dam.es.dto.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * This interface represents the repository for the OrderLine model.
 * @author BiquesDAM-Team
 */
interface IOrdersLineRepository {
    /**
     * Find all orders lines
     * @param token JWT token
     * @return Flow of OrderLineDTO
     */
    suspend fun findAll(token: String): Flow<OrderLineDTO>
    /**
     * Find order by id
     * @param token JWT token
     * @param id Order uuid
     * @return OrderLineDTO
     */
    suspend fun findById(token: String, id: UUID): OrderLineDTO
    /**
     * Create a new order line
     * @param token JWT token
     * @param dto OrderLineCreateDTO
     * @return OrderLineDTO
     */
    suspend fun save(token: String, entity: OrderLineCreateDTO): OrderLineDTO
    /**
     * Update an order line
     * @param token JWT token
     * @param id Order uuid
     * @param dto OrderLineUpdateDTO
     * @return OrderLineDTO
     */
    suspend fun update(token: String, id: UUID, entity: OrderLineUpdateDTO): OrderLineDTO
    /**
     * Delete an order line
     * @param token JWT token
     * @param id Order uuid
     * @return OrderLineDTO
     */
    suspend fun delete(token: String, id: UUID)
}