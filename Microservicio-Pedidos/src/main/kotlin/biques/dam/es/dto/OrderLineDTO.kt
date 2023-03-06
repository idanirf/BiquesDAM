package biques.dam.es.dto

import biques.dam.es.models.OrderLine
import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import java.util.UUID


@Serializable
data class OrderLineDTO(
    val id: String? = newId<OrderLine>().toString(),
    val uuid: String? = UUID.randomUUID().toString(),
    val product: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: String
) {
}