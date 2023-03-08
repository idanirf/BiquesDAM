package biques.dam.es.exceptions

/**
 * Base class for the exceptions related to the services
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class ServiceException(message: String): RuntimeException(message)

/**
 * Exception for the service not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class ServiceNotFoundException(message: String) : ServiceException(message)

/**
 * Exception for the service with error (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class ServiceBadRequestException(message: String) : ServiceException(message)

/**
 * Exception for the service with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class ServiceConflictIntegrityException(message: String) : ServiceException(message)