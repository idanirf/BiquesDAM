package biques.dam.es.repositories.orders

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderDTOUpdate
import biques.dam.es.dto.OrderSaveDTO
import java.util.*
/**
 * This interface represents the repository for the Order model.
 * @author BiquesDAM-Team
 */
interface IOrdersRepository {
    /**
     * Find all orders
     * @param token JWT token
     * @return List of orders
     */
    suspend fun findAll(token: String): List<OrderDTO>

    /**
     * Find order by id
     * @param token JWT token
     * @param id Order uuid
     * @return OrderDTO
     */
    suspend fun findById(token: String, id: UUID): OrderDTO
    /**
     * Create a new order
     * @param token JWT token
     * @param dto OrderSaveDTO
     * @return OrderDTO
     */
    suspend fun save(token: String, entity: OrderSaveDTO): OrderDTO
    /**
     * Update an order
     * @param token JWT token
     * @param id Order uuid
     * @param dto OrderDTOUpdate
     * @return OrderDTO
     */
    suspend fun update(token: String, id: UUID, entity: OrderDTOUpdate): OrderDTO
    /**
     * Delete an order
     * @param token JWT token
     * @param id Order uuid
     * @return OrderDTO
     */
    suspend fun delete(token: String, id: UUID)
}