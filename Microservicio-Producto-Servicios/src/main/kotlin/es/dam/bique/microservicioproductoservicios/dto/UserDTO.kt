package es.dam.bique.microservicioproductoservicios.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentUserDTO(
    val uuid: String,
    val email: String,
    val address: String,

)