package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServiceDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : String,
    val type : String
)

@Serializable
data class FinalServiceDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String
)

@Serializable
data class ServiceCreateDTO(
    val image: String,
    val price: Float,
    val appointment : String,
    val type : String
)
