package biques.dam.es.exceptions

/**
 * Base class for the exceptions related to the order lines
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class OrderLineException(message: String) : RuntimeException(message)

/**
 * Exception for the order line not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class OrderLineNotFoundException(message: String): OrderLineException(message)

/**
 * Exception for the order line with error (500)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class OrderLineErrorException(message: String): OrderLineException(message)

/**
 * Exception for the order line with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class OrderLineBadRequestException(message: String): OrderLineException(message)