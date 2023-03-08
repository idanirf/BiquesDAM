package biques.dam.es.repositories.orderline

import biques.dam.es.models.Order
import biques.dam.es.models.OrderLine
import biques.dam.es.repositories.CrudRepository
import org.litote.kmongo.Id
import java.util.*

/**
 * This interface represents the repository for the OrderLine model.
 * @author BiquesDAM-Team
 */
interface OrderLineRepository: CrudRepository<OrderLine, Id<OrderLine>> {

    suspend fun findByUUID(uuid: UUID): OrderLine

}