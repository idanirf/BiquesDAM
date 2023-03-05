package biques.dam.es.dto

import kotlinx.serialization.Serializable

//TODO: Cambiar dtos

@Serializable
data class OrderDTOCreate(
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<OrderLineCreateDTO>,
    val cliente: String
)

@Serializable
data class OrderSaveDTO(
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<String>,
    val cliente: String
)

@Serializable
data class OrderDTOUpdate(
    val status: String,
    val total: Double,
    val iva: Double,
    val orderLine: List<String>,
    val cliente: String
)

@Serializable
data class OrderDTO(
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val iva: Double,
    var orderLine: List<String>,
    val cliente: String
)