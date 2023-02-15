package biques.dam.es.dto

import biques.dam.es.models.Order

data class OrderDTO(
    val id: String,
    val uuid: String,
    val status: String,
    val total: Double,
    val IVA:Double,
    val OrderLine: OrderLineDTO


) {
}