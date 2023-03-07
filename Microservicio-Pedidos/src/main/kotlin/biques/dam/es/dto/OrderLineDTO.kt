package biques.dam.es.dto

import biques.dam.es.models.OrderLine
import kotlinx.serialization.Serializable
import org.litote.kmongo.newId
import java.util.UUID

/**
 * This data class represents a DTO for the OrderLine model.
 * @param id a string representing the id of the orderLine.
 * @param uuid a string representing the uuid of the orderLine.
 * @param sale a string representing the sale of the orderLine.
 * @param amount a int representing the amount of the orderLine.
 * @param price a double representing the price of the orderLine.
 * @param total a double representing the total of the orderLine.
 * @param employee a long representing the employee of the orderLine.
 * @author BiquesDAM-Team
 */
@Serializable
data class OrderLineDTO(
    val id: String? = newId<OrderLine>().toString(),
    val uuid: String? = UUID.randomUUID().toString(),
    val sale: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: Long
) {
}