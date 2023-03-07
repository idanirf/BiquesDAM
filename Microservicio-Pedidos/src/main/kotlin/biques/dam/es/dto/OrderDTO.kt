package biques.dam.es.dto

import biques.dam.es.models.Order
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
data class OrderDTO(
    val id: String? = newId<Order>().toString(),
    val uuid: String = UUID.randomUUID().toString(),
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: List<String>,
    val cliente: Long
)

data class OrderUpdateDTO(
    val id: String,
    val uuid: String ,
    val status: String,
    val total: Double,
    val iva:Double,
    val orderLine: List<String>,
    val cliente: Long
)



@Serializable
data class OrderAllDTO(
    val data: List<OrderDTO>
)