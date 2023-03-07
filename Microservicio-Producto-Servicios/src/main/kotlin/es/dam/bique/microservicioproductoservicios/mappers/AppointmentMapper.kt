package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.AppointmentCreateDTO
import es.dam.bique.microservicioproductoservicios.dto.AppointmentDTO
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.models.AssistanceType
import java.time.LocalDate
import java.util.*

/**
 * Creates an AppointmentDTO from an Appointment
 * @return AppointmentDTO
 * @author The BiquesDam Team
 */
fun Appointment.toDTO(): AppointmentDTO{
    return AppointmentDTO(
        id = id,
        uuid = uuid.toString(),
        user = userId.toString(),
        assistance = assistance.type,
        date = date.toString(),
        description = description
    )
}

/**
 * Creates an Appointment from an AppointmentCreateDTO
 * @return Appointment
 * @author The BiquesDam Team
 */
fun AppointmentCreateDTO.toModel(uuid: UUID): Appointment{
    return Appointment(
        uuid = uuid,
        userId = UUID.fromString(userId),
        assistance = AssistanceType.from(assistance),
        date = LocalDate.parse(date),
        description = this.description
    )
}