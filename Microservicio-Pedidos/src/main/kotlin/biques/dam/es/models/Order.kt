package biques.dam.es.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import biques.dam.es.serializers.UUIDSerializer
import java.util.UUID

@Serializable
data class Order(
    @BsonId @Contextual
    val id: Id<Order> = newId(),
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),
    @Contextual
    val status: StatusOrder,
    val total: Double,
    val IVA: Double,
    @Contextual
    val orderLine: Id<OrderLine>,
    @Serializable(with = UUIDSerializer::class)
    val cliente: UUID
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

