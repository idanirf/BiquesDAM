package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val IVA:Double,
    val OrderLine: List<OrderLineDTO>
) {
}