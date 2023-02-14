package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


sealed class DatabaseException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class DataBaseIntegrityViolationException(message: String? = null) : DatabaseException(message)