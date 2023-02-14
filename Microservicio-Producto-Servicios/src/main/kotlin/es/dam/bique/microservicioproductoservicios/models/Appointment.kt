package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.dto.AppointmentUserDTO
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Table(name = "APPOINTMENTS")
data class Appointment(
    @Id
    val id: Long? = null,

    val uuid: UUID = UUID.randomUUID(),

    @NotEmpty(message = "El usuario que realiza la cita no puede estar vacío.")
    val user: AppointmentUserDTO,

    @NotEmpty(message = "El tipo de asistencia no puede estar vacía.")
    val assistance : AssistanceType,

    @NotEmpty(message = "La fecha no puede estar vacía.")
    val date: LocalDateTime,

    val description: String
)

enum class AssistanceType{
    ANY, TECHNICAL
}
