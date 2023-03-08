package biques.dam.es.utils

import biques.dam.es.exceptions.UUIDException
import java.util.*

/**
 * Extension function to convert a String to a UUID
 * @return UUID
 * @throws UUIDException if the String is not in the UUID format
 */
fun String.toUUID(): UUID{
    return try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        throw UUIDException("The id is invalid or not in the UUID format")
    }
}