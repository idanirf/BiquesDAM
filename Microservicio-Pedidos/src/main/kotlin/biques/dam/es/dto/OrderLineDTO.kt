package biques.dam.es.dto

import kotlinx.serialization.Serializable


@Serializable
data class OrderLineDTO(
    val id: String,
    val uuid: String,
    // val product: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    // val employee: String
) {
}