package biques.dam.es.exceptions


sealed class OrderLineException(message: String) : RuntimeException(message)

class OrderLineNotFoundException(message: String): OrderLineException(message)
class OrderLineErrorException(message: String): OrderLineException(message)
class OrderLineBadRequestException(message: String): OrderLineException(message)