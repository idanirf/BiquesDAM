package es.dam.bique.microservicioproductoservicios.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

/**
 * Serializer for UUID
 * @author The BiquesDAM Team
 */
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    /**
     * Deserialize a UUID from a string
     * @param decoder Decoder
     * @return UUID
     * @author The BiquesDAM Team
     */
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    /**
     * Serialize a UUID to a string
     * @param encoder Encoder
     * @param value UUID to serialize
     * @return String with the UUID serialized
     * @author The BiquesDAM Team
     */
    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}