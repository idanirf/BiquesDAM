package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.LocalDateSerializer
import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

/**
 * Represents the appointments an user is able to do.
 * @param id Appointment identifier.
 * @param uuid Appointment unique identifier.
 * @param userId User identifier.
 * @param assistance Type of assistance.
 * @param date Date of the appointment.
 * @param description Description of the appointment.
 * @author The BiquesDam Team.
 */
@Serializable
@Table("Appointments")
data class Appointment(

    @Id
    val id: Long? = null,

    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,

    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,

    @Contextual
    val assistance : AssistanceType,

    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,

    val description: String
)

/**
 * Represents the type of assistance.
 * @param type Value of the assistance type.
 */
enum class AssistanceType(val type: String){
    ANY("ANY"),
    TECHNICAL("TECHNICAL");
    companion object {

        /** Returns the AssistanceType from a string. */
        fun from(tipo: String): AssistanceType {
            return when (tipo.uppercase()){
                "ANY" -> ANY
                "TECHNICAL" -> TECHNICAL
                else -> throw IllegalArgumentException("Assistance type unknown.")
            }
        }

    }
}

