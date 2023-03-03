package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.LocalDateSerializer
import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

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

