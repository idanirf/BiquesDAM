package biques.dam.es.dto

import kotlinx.serialization.Serializable

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

@Serializable
data class OrderLineCreateDTO(
    val sale: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: Long
)

@Serializable
data class OrderLineUpdateDTO(
    val sale: String,
    val amount: Int,
    val price: Double,
    val total: Double,
    val employee: Long
)