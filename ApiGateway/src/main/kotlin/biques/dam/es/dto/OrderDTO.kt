package biques.dam.es.dto

import kotlinx.serialization.Serializable


/**
 * Data transfer object for creating an Order.
 * @param status The status of the order.
 * @param total The total amount of the order.
 * @param iva The value of the tax applied to the order.
 * @param orderLine The list of OrderLineCreateDTO objects associated with the order.
 * @param cliente The client ID associated with the order.
 */
@Serializable
data class OrderDTOCreate(
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<OrderLineCreateDTO>,
    val cliente: Long
)


/**
 * Data transfer object for saving a new order.
 * @param status The status of the order.
 * @param total The total amount of the order.
 * @param iva The amount of the order's tax.
 * @param orderLine The list of order lines associated with the order.
 * @param cliente The ID of the client who made the order.
 */
@Serializable
data class OrderSaveDTO(
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<String>,
    val cliente: Long
)

/**
 * Data transfer object for updating an existing order.
 * @param status The updated status of the order.
 * @param total The updated total amount of the order.
 * @param iva The updated amount of the order's tax.
 * @param orderLine The updated list of order lines associated with the order.
 * @param cliente The updated ID of the client who made the order.
 */
@Serializable
data class OrderDTOUpdate(
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<String>,
    val cliente: Long
)

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
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val iva: Double,
    var orderLine: List<String>,
    val cliente: Long
)

/**
 * Data transfer object for representing a list of orders.
 * @property data The list of OrderDTO objects.
 */
data class OrderAllDTO(
    val data: List<OrderDTO>,
)