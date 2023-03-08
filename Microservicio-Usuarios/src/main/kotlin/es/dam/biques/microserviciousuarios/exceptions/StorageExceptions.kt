package es.dam.biques.microserviciousuarios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * An exception indicating that an error occurred while accessing storage.
 * @param message a message describing the exception
 * @author BiquesDAM-Team
 */
sealed class StorageException(message: String) : RuntimeException(message)

/**
 * An exception indicating that the requested file could not be found in storage.
 * @param message a message describing the exception
 * @author BiquesDAM-Team
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class StorageNotFoundException(message: String) : StorageException(message)

/**
 * An exception indicating that a request related to file storage is malformed or invalid.
 * @param message a message describing the exception
 * @author BiquesDAM-Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class StorageBadRequestException(message: String) : StorageException(message)