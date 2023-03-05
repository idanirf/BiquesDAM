package biques.dam.es.dto

//TODO Cambiar dtos


data class OrderDTOCreate(
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: String,
    val cliente: String
)

data class OrderDTOUpdate(
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: String,
    val cliente: String
)

data class OrderDTO(
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: String,
    val cliente: String
)