package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

import java.util.*

@Serializable
@Table("Services")
data class Service(

    @Id
    override val id: Long? = null,

    @Serializable(with = UUIDSerializer::class)
    override val uuid: UUID,

    val image: String,

    val price: Float = 0.0f,

    @Serializable(with = UUIDSerializer::class)
    val appointment: UUID,

    @Contextual
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