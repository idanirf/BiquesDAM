package biques.dam.es.dto

import biques.dam.es.models.Order
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

/**
 * This data class represents a DTO for the Order model.
 * @param id a string representing the id of the order.
 * @param uuid a string representing the uuid of the order.
 * @param status a string representing the status of the order.
 * @param total a double representing the total of the order.
 * @param iva a double representing the iva of the order.
 * @param orderLine a list of strings representing the orderLine of the order.
 * @param cliente a long representing the cliente of the order.
 * @author BiquesDAM-Team
 */
@Serializable
data class OrderDTO(
    val id: String? = newId<Order>().toString(),
    val uuid: String = UUID.randomUUID().toString(),
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: List<String>,
    val cliente: Long
)

/**
 * This data class represents an update request for an order.
 * @param id a string representing the id of the order.
 * @param uuid a string representing the uuid of the order.
 * @param status a string representing the status of the order.
 * @param total a double representing the total of the order.
 * @param iva a double representing the iva of the order.
 * @param orderLine a list of strings representing the orderLine of the order.
 * @param cliente a long representing the cliente of the order.
 * @author BiquesDAM-Team
 */
data class OrderUpdateDTO(
    val id: String,
    val uuid: String ,
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: List<String>,
    val cliente: Long
)

/**
 * This data class represents a DTO for a list of orders.
 * @param data a list of OrderDTO objects representing the orders.
 * @author BiquesDAM-Team
 */

@Serializable
data class OrderAllDTO(
    val data: List<OrderDTO>
)