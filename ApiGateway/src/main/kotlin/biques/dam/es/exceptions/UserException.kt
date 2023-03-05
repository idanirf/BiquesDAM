package biques.dam.es.exceptions

sealed class UserExceptions(message: String) : RuntimeException(message)

class UserNotFoundException(message: String) : UserExceptions(message)

class UserBadRequestException(message: String) : UserExceptions(message)