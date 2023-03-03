package es.dam.biques.microserviciousuarios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class StorageException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class StorageNotFoundException(message: String) : StorageException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class StorageBadRequestException(message: String) : StorageException(message)