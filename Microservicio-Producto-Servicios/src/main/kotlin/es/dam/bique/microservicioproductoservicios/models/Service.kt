package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Represents the services that are on sale.
 * @param id Service identifier.
 * @param uuid Service unique identifier.
 * @param image Service image.
 * @param price Service price.
 * @param appointment Service appointment identifier.
 * @param type Service type.
 * @author The BiquesDam Team.
 */
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

/**
 * Represents the type of service.
 * @param type Value of the service type.
 */
enum class ServiceType(val type: String) {
    REVISION("REVISION"),
    ASSEMBLY("ASSEMBLY"),
    REPLACEMENT("REPLACEMENT"),
    REPAIR("REPAIR");
    companion object {

        /** Returns the ServiceType from a string. */
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