package biques.dam.es.exceptions

sealed class ServiceException(message: String): RuntimeException(message)

class ServiceNotFoundException(message: String) : ServiceException(message)

class ServiceBadRequestException(message: String) : ServiceException(message)

class ServiceConflictIntegrityException(message: String) : ServiceException(message)