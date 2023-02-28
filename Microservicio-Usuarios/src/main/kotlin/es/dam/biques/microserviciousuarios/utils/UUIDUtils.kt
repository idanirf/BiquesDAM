package es.dam.biques.microserviciousuarios.utils

import java.util.*

class UUIDException(message: String) : Exception(message)

fun String.toUUID(): UUID {
    return try {
        UUID.fromString(this.trim())
    } catch (e: IllegalArgumentException) {
        throw UUIDException("The id is invalid or not in the UUID format")
    }
}