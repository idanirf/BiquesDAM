package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class ProductException(message: String): RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException(message: String) : ProductException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ProductBadRequestException(message: String) : ProductException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ProductConflictIntegrityException(message: String) : ProductException(message)