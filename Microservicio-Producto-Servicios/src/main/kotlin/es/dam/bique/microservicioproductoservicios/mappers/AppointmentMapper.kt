package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.AppointmentCreateDTO
import es.dam.bique.microservicioproductoservicios.dto.AppointmentDTO
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.models.AssistanceType
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun AppointmentDTO.toEntity(): Appointment{
    return Appointment(
        id = id,
        uuid = UUID.fromString(uuid),
        userId = UUID.fromString(user),
        assistance = AssistanceType.from(assistance),
        date = LocalDate.parse(date),
        description = this.description
    )
}

fun Appointment.toDTO(): AppointmentDTO{
    return AppointmentDTO(
        id = id,
        uuid = uuid.toString(),
        user = userId.toString(),
        assistance = assistance.value,
        date = date.toString(),
        description = description
    )
}

fun AppointmentCreateDTO.toModel(uuid: UUID): Appointment{
    return Appointment(
        uuid = uuid,
        userId = UUID.fromString(userId),
        assistance = AssistanceType.from(assistance),
        date = LocalDate.parse(date),
        description = this.description
    )
}