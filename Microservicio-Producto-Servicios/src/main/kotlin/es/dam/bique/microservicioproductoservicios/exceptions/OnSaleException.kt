package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class OnSaleException(message: String): RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class OnSaleNotFoundException(message: String) : OnSaleException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class OnSaleBadRequestException(message: String) : OnSaleException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class OnSaleConflictIntegrityException(message: String) : OnSaleException(message)