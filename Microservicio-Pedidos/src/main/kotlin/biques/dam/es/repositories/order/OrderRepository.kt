package biques.dam.es.repositories.order

import biques.dam.es.models.Order
import biques.dam.es.repositories.CrudRepository
import org.litote.kmongo.Id
import java.util.*

interface OrderRepository: CrudRepository<Order, Id<Order>> {
    suspend fun findByUUID(uuid: UUID): Order
}