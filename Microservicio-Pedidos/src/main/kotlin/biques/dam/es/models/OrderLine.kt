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
data class OrderLine(
    @BsonId @Contextual
    val id: Id<OrderLine> = newId(),
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),
    @Serializable(with = UUIDSerializer::class)
    val product: UUID,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: Long
    )
