package biques.dam.es.exceptions

sealed class ProductException(message: String): RuntimeException(message)

class ProductNotFoundException(message: String) : ProductException(message)

class ProductBadRequestException(message: String) : ProductException(message)

class ProductConflictIntegrityException(message: String) : ProductException(message)