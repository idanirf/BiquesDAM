package es.dam.bique.microservicioproductoservicios.dto

import es.dam.bique.microservicioproductoservicios.models.OnSale

data class ServiceDTO(

    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String

)

data class ServiceCreateDTO(

    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String

)