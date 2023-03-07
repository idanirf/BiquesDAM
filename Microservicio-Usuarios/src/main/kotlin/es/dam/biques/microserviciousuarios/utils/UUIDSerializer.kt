package es.dam.biques.microserviciousuarios.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    /**
     * Deserializes a UUID object from the provided decoder.
     * @param decoder the decoder to use for deserialization
     * @return the UUID object obtained by deserializing the input string
     * @throws IllegalArgumentException if the input string is not a valid UUID
     * @author BiquesDAM-Team
     */
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    /**
     * Serializes a UUID object using the provided encoder.
     * @param encoder the encoder to use for serialization
     * @param value the UUID object to be serialized
     * @throws EncodingException if there is an error during the encoding process
     * @author BiquesDAM-Team
     */
    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}