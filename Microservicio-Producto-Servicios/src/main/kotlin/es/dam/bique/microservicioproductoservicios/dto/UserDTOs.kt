package es.dam.bique.microservicioproductoservicios.dto

import java.util.*

data class AppointmentUserDTO(
    val uuid: UUID,
    val email: String,
    val address: String
)