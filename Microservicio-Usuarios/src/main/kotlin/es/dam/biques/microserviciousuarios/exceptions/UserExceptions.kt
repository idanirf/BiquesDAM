package es.dam.biques.microserviciousuarios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class UsuarioExceptions(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class UsuarioNotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UsuarioBadRequestException(message: String) : RuntimeException(message)