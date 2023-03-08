package es.dam.bique.microservicioproductoservicios.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

/**
 * Serializer for LocalDate objects.
 * @author The BiquesDAM team
 */
object LocalDateSerializer : KSerializer<LocalDate> {

    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    /**
     * Deserializes a LocalDate object from a string.
     * @param decoder Decoder to use.
     * @return LocalDate object.
     * @author The BiquesDAM team
     */
    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }

    /**
     * Serializes a LocalDate object to a string.
     * @param encoder Encoder to use.
     * @param value LocalDate object to serialize.
     * @author The BiquesDAM team
     */
    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

}