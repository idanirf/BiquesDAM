package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.dto.AppointmentUserDTO
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "APPOINTMENTS")
data class Appointment(
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
