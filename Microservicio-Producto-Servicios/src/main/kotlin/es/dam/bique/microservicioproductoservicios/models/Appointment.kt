package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.dto.AppointmentUserDTO
import es.dam.bique.microservicioproductoservicios.serializers.LocalDateTimeSerializer
import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import jakarta.validation.constraints.NotEmpty
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table(name = "APPOINTMENTS")
data class Appointment(

    @BsonId @Contextual
    val id: Id<Appointment> = newId(),

    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),

    @NotEmpty(message = "The user cannot be empty.")
    val user: AppointmentUserDTO,

    @Contextual
    @NotEmpty(message = "Assistance type cannot be empty.")
    val assistance : AssistanceType,

    @Serializable(with = LocalDateTimeSerializer::class)
    @NotEmpty(message = "The date field cannot be empty.")
    val date: LocalDateTime,

    val description: String
)

enum class AssistanceType(val value: String){
    ANY("ANY"),
    TECHNICAL("TECHNICAL");
    companion object {
        fun from(tipo: String): AssistanceType {
            return when (tipo.uppercase()){
                "ANY" -> ANY
                "TECHNICAL" -> TECHNICAL
                else -> throw IllegalArgumentException("Assistance type unknown.")
            }
        }
    }
}

