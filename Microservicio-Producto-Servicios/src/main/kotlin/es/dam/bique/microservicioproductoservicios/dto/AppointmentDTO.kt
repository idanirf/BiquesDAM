package es.dam.bique.microservicioproductoservicios.dto

/**
 * Data Transfer Object for Appointment
 * @param id Appointment identifier
 * @param uuid Appointment UUID
 * @param user Appointment user
 * @param assistance Appointment assistance
 * @param date Appointment date
 * @param description Appointment description
 * @author The BiquesDam Team
 */
data class AppointmentDTO(
    val id: Long?,
    val uuid: String,
    val user: String,
    val assistance : String,
    val date: String,
    val description: String
)

/**
 * Data Transfer Object for AppointmentCreate
 * @param userId Appointment user identifier
 * @param assistance Appointment assistance
 * @param date Appointment date
 * @param description Appointment description
 * @author The BiquesDam Team
 */
data class AppointmentCreateDTO(
    val userId: String,
    val assistance : String,
    val date: String,
    val description: String
)