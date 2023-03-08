package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception for the exceptions related to Service
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class ServiceException(message: String): RuntimeException(message)

/**
 * Exception for the Service not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class ServiceNotFoundException(message: String) : ServiceException(message)

/**
 * Exception for the Service with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class ServiceBadRequestException(message: String) : ServiceException(message)

/**
 * Exception for the Service with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class ServiceConflictIntegrityException(message: String) : ServiceException(message)