package es.dam.bique.microservicioproductoservicios.dto

data class AppointmentDTO(

    val id: Long?,
    val uuid: String,
    val user: AppointmentUserDTO,
    val assistance : String,
    val date: String,
    val description: String

)

data class AppointmentCreateDTO(

    val user: AppointmentUserDTO,
    val assistance : String,
    val date: String,
    val description: String

)