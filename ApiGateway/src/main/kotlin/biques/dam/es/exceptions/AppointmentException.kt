package biques.dam.es.exceptions

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
class AppointmentNotFoundException(message: String) : AppointmentException(message)

/**
 * Exception for the appointment with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class AppointmentBadRequestException(message: String) : AppointmentException(message)

/**
 * Exception for the appointment with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class AppointmentConflictIntegrityException(message: String) : AppointmentException(message)