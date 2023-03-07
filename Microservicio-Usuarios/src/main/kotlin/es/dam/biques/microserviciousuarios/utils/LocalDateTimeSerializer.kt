package es.dam.biques.microserviciousuarios.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    /**
     * Deserializes a LocalDateTime object from the provided decoder.
     * @param decoder the decoder to use for deserialization
     * @return the LocalDateTime object obtained by deserializing the input string
     * @throws DateTimeParseException if the input string cannot be parsed into a valid LocalDateTime object
     * @author BiquesDAM-Team
     */
    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }

    /**
     * Serializes a LocalDateTime object using the provided encoder.
     * @param encoder the encoder to use for serialization
     * @param value the LocalDateTime object to be serialized
     * @throws EncodingException if there is an error during the encoding process
     * @author BiquesDAM-Team
     */
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }
}