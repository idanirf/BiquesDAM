package biques.dam.es.exceptions

sealed class SaleException(message: String): RuntimeException(message)

class SaleNotFoundException(message: String) : SaleException(message)

class SaleBadRequestException(message: String) : SaleException(message)

class SaleConflictIntegrityException(message: String) : SaleException(message)

