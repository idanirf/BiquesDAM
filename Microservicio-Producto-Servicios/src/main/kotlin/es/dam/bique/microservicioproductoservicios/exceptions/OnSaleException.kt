package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception for the exceptions related to OnSale
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class OnSaleException(message: String): RuntimeException(message)

/**
 * Exception for the OnSale not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class OnSaleNotFoundException(message: String) : OnSaleException(message)

/**
 * Exception for the OnSale with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class OnSaleBadRequestException(message: String) : OnSaleException(message)

/**
 * Exception for the OnSale with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class OnSaleConflictIntegrityException(message: String) : OnSaleException(message)