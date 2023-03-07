package es.dam.bique.microservicioproductoservicios.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Base class for the exceptions related to the appointments
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class AppointmentException(message: String): RuntimeException(message)

/**
 * Exception for the appointment not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class AppointmentNotFoundException(message: String) : AppointmentException(message)

/**
 * Exception for the appointment with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class AppointmentBadRequestException(message: String) : AppointmentException(message)

/**
 * Exception for the appointment with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class AppointmentConflictIntegrityException(message: String) : AppointmentException(message)