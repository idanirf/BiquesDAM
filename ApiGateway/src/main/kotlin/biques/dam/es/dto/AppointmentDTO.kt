package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentDTO(
    val id: Long?,
    val uuid: String,
    val user: String,
    val assistance : String,
    val date: String,
    val description: String
)

@Serializable
data class AppointmentCreateDTO(
    val userId: String,
    val assistance : String,
    val date: String,
    val description: String
)