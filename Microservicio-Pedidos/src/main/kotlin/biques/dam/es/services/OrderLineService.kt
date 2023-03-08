package biques.dam.es.services

import biques.dam.es.models.OrderLine
import biques.dam.es.repositories.orderline.OrderLineRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Service that provides methods to interact with the OrderLineRepository.
 * @author BiquesDAM-Team
 */
class OrderLineService(
    private val orderLineRepository: OrderLineRepository,
) {
    /**
     * Returns a Flow containing all OrderLine objects in the database.
     * @return a Flow containing all OrderLine objects in the database.
     */
    fun getAllOrderLine(): Flow<OrderLine> {
        return orderLineRepository.findAll()
    }

    /**
     * Finds an OrderLine in the database by its UUID.
     * @param uuid the UUID of the order line to find.
     * @return the OrderLine object corresponding to the specified UUID.
     */
    suspend fun getOrderLineByUUID(uuid: UUID): OrderLine {
        return orderLineRepository.findByUUID(uuid)
    }

    /**
     * Saves an OrderLine into the repository.
     * @param orderLine The [OrderLine] to be saved.
     * @return The saved [OrderLine].
     */
    suspend fun saveOrderLine(orderLine: OrderLine): OrderLine {
        return orderLineRepository.save(orderLine)
    }

    /**
     * Updates an OrderLine in the repository.
     *
     * @param orderLine The OrderLine to be updated.
     * @return The updated OrderLine.
     */
    suspend fun updateOrderLine(orderLine: OrderLine): OrderLine {
        return orderLineRepository.update(orderLine)
    }

    /**
     * Deletes an OrderLine from the repository.
     *
     * @param orderLine The OrderLine to be deleted.
     * @return True if the OrderLine was successfully deleted, false otherwise.
     */
    suspend fun deleteOrderLine(orderLine: OrderLine): Boolean {
        return orderLineRepository.delete(orderLine)
    }

}
