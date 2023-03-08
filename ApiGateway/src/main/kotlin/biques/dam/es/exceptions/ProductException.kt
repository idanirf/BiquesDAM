package biques.dam.es.exceptions

/**
 * Base class for the exceptions related to the products
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class ProductException(message: String): RuntimeException(message)

/**
 * Exception for the product not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class ProductNotFoundException(message: String) : ProductException(message)

/**
 * Exception for the product with error (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class ProductBadRequestException(message: String) : ProductException(message)

/**
 * Exception for the product with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class ProductConflictIntegrityException(message: String) : ProductException(message)