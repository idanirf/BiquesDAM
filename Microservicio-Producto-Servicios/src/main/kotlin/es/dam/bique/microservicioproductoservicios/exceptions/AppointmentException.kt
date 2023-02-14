package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class AppointmentException(message: String): RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class AppointmentNotFoundException(message: String) : ProductException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AppointmentBadRequestException(message: String) : ProductException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AppointmentConflictIntegrityException(message: String) : ProductException(message)