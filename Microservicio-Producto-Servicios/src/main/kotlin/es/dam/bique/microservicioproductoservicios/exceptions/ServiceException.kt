package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class ServiceException(message: String): RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ServiceNotFoundException(message: String) : ServiceException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ServiceBadRequestException(message: String) : ServiceException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ServiceConflictIntegrityException(message: String) : ServiceException(message)