package biques.dam.es.exceptions

/**
 * Base class for the exceptions related to the users
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class UserExceptions(message: String) : RuntimeException(message)

/**
 * Exception for the user not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class UserNotFoundException(message: String) : UserExceptions(message)

/**
 * Exception for the user with error (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class UserBadRequestException(message: String) : UserExceptions(message)