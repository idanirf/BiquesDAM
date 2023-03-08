package biques.dam.es.dto

import kotlinx.serialization.Serializable

/**
 * This data class represents a DTO for the Order model.
 * @param uuid a string representing the uuid of the order.
 * @param status a string representing the status of the order.
 * @param total a double representing the total of the order.
 * @param iva a double representing the iva of the order.
 * @param orderLine a list of strings representing the orderLine of the order.
 * @param cliente a long representing the cliente of the order.
 */
@Serializable
data class FinalOrderDTO(
    val uuid: String,
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<OrderLineDTO>,
    val cliente: UserResponseDTO
)
