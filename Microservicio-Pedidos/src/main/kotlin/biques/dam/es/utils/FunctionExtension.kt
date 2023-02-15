package biques.dam.es.utils

import biques.dam.es.exceptions.UUIDException
import java.util.*


fun String.toUUID(): UUID{
    return try {
        UUID.fromString(this.trim())
    } catch (e: IllegalArgumentException) {
        throw UUIDException("The id is invalid or not in the UUID format")
    }
}