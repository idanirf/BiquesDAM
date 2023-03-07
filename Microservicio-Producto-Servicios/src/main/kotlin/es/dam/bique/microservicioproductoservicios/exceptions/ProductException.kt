package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception for the exceptions related to Product
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class ProductException(message: String): RuntimeException(message)

/**
 * Exception for the Product not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException(message: String) : ProductException(message)

/**
 * Exception for the Product with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class ProductBadRequestException(message: String) : ProductException(message)

/**
 * Exception for the Product with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class ProductConflictIntegrityException(message: String) : ProductException(message)