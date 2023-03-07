package biques.dam.es.dto

import kotlinx.serialization.Serializable

/**
 * This data class represents a DTO for the Appointment model.
 * @param id a long representing the id of the appointment.
 * @param uuid a string representing the uuid of the appointment.
 * @param user a string representing the user of the appointment.
 * @param assistance a string representing the assistance of the appointment.
 * @param date a string representing the date of the appointment.
 * @param description a string representing the description of the appointment.
 * @author BiquesDAM-Team
 */
@Serializable
data class AppointmentDTO(
    val id: Long?,
    val uuid: String,
    val user: String,
    val assistance : String,
    val date: String,
    val description: String
)

/**
 * This data class represents a create request for an appointment.
 * @param userId a string representing the user of the appointment.
 * @param assistance a string representing the assistance of the appointment.
 * @param date a string representing the date of the appointment.
 * @param description a string representing the description of the appointment.
 * @author BiquesDAM-Team
 */
@Serializable
data class AppointmentCreateDTO(
    val userId: String,
    val assistance : String,
    val date: String,
    val description: String
)