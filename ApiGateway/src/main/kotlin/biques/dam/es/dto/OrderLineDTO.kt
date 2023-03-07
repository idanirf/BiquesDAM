package biques.dam.es.dto

import kotlinx.serialization.Serializable

/**
 * Data class representing an order line in a simplified format for serialization.
 * @param id The unique identifier of the order line.
 * @param uuid The UUID of the order line.
 * @param sale The sale associated with the order line.
 * @param amount The amount of the product associated with the order line.
 * @param price The price of the product associated with the order line.
 * @param total The total price of the order line.
 * @param employee The employee associated with the order line.
 */
@Serializable
data class OrderLineDTO(
    val id: String?,
    val uuid: String?,
    var sale: String?,
    val amount: Int?,
    val price: Double?,
    val total: Double?,
    val employee: Long?
)

/**
 * Data class representing an order line in a simplified format for creation.
 * @param sale The sale associated with the order line.
 * @param amount The amount of the product associated with the order line.
 * @param price The price of the product associated with the order line.
 * @param total The total price of the order line.
 * @param employee The employee associated with the order line.
 */
@Serializable
data class OrderLineCreateDTO(
    val sale: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: Long
)

/**
 * Data class representing an order line in a simplified format for update.
 * @param sale The sale associated with the order line.
 * @param amount The amount of the product associated with the order line.
 * @param price The price of the product associated with the order line.
 * @param total The total price of the order line.
 * @param employee The employee associated with the order line.
 */
@Serializable
data class OrderLineUpdateDTO(
    val sale: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: Long
)