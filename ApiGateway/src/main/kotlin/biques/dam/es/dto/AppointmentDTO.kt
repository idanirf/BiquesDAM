package biques.dam.es.dto

data class AppointmentDTO(
    val id: Long?,
    val uuid: String,
    val user: String,
    val assistance : String,
    val date: String,
    val description: String
)

data class AppointmentCreateDTO(
    val userId: String,
    val assistance : String,
    val date: String,
    val description: String
)