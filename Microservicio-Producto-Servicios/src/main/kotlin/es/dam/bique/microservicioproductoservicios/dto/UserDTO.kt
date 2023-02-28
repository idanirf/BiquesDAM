package es.dam.bique.microservicioproductoservicios.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppointmentUserDTO(
    val email: String,
    val address: String,

)