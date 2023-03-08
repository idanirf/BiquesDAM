package biques.dam.es.exceptions

/**
 * Base class for the exceptions related to the sales
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
sealed class SaleException(message: String): RuntimeException(message)

/**
 * Exception for the sale not found (404)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class SaleNotFoundException(message: String) : SaleException(message)

/**
 * Exception for the sale with error (400)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class SaleBadRequestException(message: String) : SaleException(message)

/**
 * Exception for the sale with conflict integrity (409)
 * @param message Message of the exception
 * @author The BiquesDAM Team
 */
class SaleConflictIntegrityException(message: String) : SaleException(message)