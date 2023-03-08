package es.dam.biques.microserviciousuarios.exceptions


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception that represents an invalid token.
 * @param message A message describing the exception.
 * @author BiquesDAM-Team
 */
sealed class TokenException(message: String) : RuntimeException(message)

/**
 * Exception that represents an invalid token.
 * @param message A message describing the exception.
 * @author BiquesDAM-Team
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class TokenInvalidException(message: String) : TokenException(message)