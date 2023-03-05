package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class FinalOrderDTO(
    val uuid: String,
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<OrderLineDTO>,
    val cliente: UserResponseDTO
)
