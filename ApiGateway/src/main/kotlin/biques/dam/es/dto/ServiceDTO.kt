package biques.dam.es.dto

import kotlinx.serialization.Serializable

/**
 * This data class represents a DTO for the Service model.
 * @param id a long representing the id of the service.
 * @param uuid a string representing the uuid of the service.
 * @param image a string representing the image of the service.
 * @param price a float representing the price of the service.
 * @param appointment a string representing the appointment of the service.
 * @param type a string representing the type of the service.
 * @author BiquesDAM-Team
 */
@Serializable
data class ServiceDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : String,
    val type : String
)


/**
 * Data transfer object for the service entity which uses appointmentDTO.
 * @param id a long representing the id of the service.
 * @param uuid a string representing the uuid of the service.
 * @param image a string representing the image of the service.
 * @param price a float representing the price of the service.
 * @param appointment a string representing the appointment of the service.
 * @param type a string representing the type of the service.
 */
@Serializable
data class FinalServiceDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : AppointmentDTO,
    val type : String
)

/**
 * This data class represents a create request for a service.
 * @param image The service image.
 * @param price The service price.
 * @param appointment The service appointment.
 * @param type The service type.
 */
@Serializable
data class ServiceCreateDTO(
    val image: String,
    val price: Float,
    val appointment : String,
    val type : String
)
