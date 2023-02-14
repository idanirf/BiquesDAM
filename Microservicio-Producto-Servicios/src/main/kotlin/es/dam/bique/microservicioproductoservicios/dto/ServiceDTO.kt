package es.dam.bique.microservicioproductoservicios.dto

import es.dam.bique.microservicioproductoservicios.models.ProductType
import java.util.UUID

data class ServiceDTO(

    val id: Long?,
    val uuid: UUID,
    val imagen: String,
    val price: Float,
    val appointmentDTO : AppointmentDTO,
    val type : ProductType

)

data class ServiceDTOCreate(

    val uuid: UUID,
    val imagen: String,
    val price: Float,
    val appointmentDTO : AppointmentDTO,
    val type : ProductType

)

data class ServiceDTOUpdate(

    val uuid: UUID,
    val imagen: String,
    val price: Float,
    val appointmentDTO : AppointmentDTO,
    val type : ProductType

)