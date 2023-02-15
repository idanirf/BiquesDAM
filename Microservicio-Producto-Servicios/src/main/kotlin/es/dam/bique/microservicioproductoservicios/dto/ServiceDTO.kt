package es.dam.bique.microservicioproductoservicios.dto

import es.dam.bique.microservicioproductoservicios.models.ProductType
import java.util.UUID

data class ServiceDTO(

    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String

)

data class ServiceDTOCreate(

    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String

)

data class ServiceDTOUpdate(

    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String

)