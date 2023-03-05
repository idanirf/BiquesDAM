package biques.dam.es.exceptions


sealed class OrderException(message: String) : RuntimeException(message)

class OrderNotFoundException(message: String) :OrderException(message)
class OrderErrorException(message: String) :OrderException(message)