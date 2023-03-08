package biques.dam.es.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import biques.dam.es.serializers.UUIDSerializer
import java.util.UUID

/**
 * Order model
 * @param id Order id
 * @param uuid Order uuid
 * @param status Order status
 * @param total Order total
 * @param iva Order iva
 * @param orderLine Order orderLine
 * @param cliente Order cliente
 * @return Order
 * @author BiquesDAM-Team
 */
@Serializable
data class Order(
    @BsonId @Contextual
    val id: Id<Order> = newId(),
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),
    @Contextual
    val status: StatusOrder,
    val total: Double,
    val iva: Double,
    val orderLine: List<String>,
    val cliente: Long
) {
    enum class StatusOrder(statusOrder: String) {
        IN_PROGRESS("IN_PROGRESS"),
        FINISHED("FINISHED"),
        DELIVERED("DELIVERED");

        companion object {
            fun from(statusOrder: String): StatusOrder {
                return when (statusOrder.uppercase()) {
                    "IN_PROGRESS" -> IN_PROGRESS
                    "FINISHED" -> FINISHED
                    "DELIVERED" -> DELIVERED
                    else -> throw IllegalArgumentException("Nudos no válidos")
                }
            }
        }
    }
}

