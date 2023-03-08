package biques.dam.es.exceptions

/**
 * Base class for the exceptions related to the orders
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class OrderException(message: String) : RuntimeException(message)

/**
 * Exception for the order not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class OrderNotFoundException(message: String): OrderException(message)

/**
 * Exception for the order with error (500)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class OrderErrorException(message: String) : OrderException(message)

/**
 * Exception for the order with bad request (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class OrderBadRequestException(message: String): OrderException(message)