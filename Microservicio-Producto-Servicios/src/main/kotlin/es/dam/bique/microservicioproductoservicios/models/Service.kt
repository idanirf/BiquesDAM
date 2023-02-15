package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "SERVICES")
data class Service(

    @BsonId @Contextual
    val id: Id<Service> = newId(),

    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),

    @NotEmpty(message = "The image cannot be empty.")
    val image: String,

    @Min(value = 0, message = "The price cannot be negative.")
    val price: Float = 0.0f,

    val appointment: Appointment,

    @Contextual
    @NotEmpty(message = "The type cannot be empty.")
    val type: ServiceType

): OnSale

enum class ServiceType(val value: String) {
    REVISION("REVISION"),
    ASSEMBLY("ASSEMBLY"),
    REPLACEMENT("REPLACEMENT"),
    REPAIR("REPAIR");
    companion object {
        fun from(tipo: String): ServiceType {
            return when (tipo.uppercase()) {
                "REVISION" -> REVISION
                "ASSEMBLY" -> ASSEMBLY
                "REPLACEMENT" -> REPLACEMENT
                "REPAIR" -> REPAIR
                else -> throw IllegalArgumentException("Service unknown.")
            }
        }
    }
}