package biques.dam.es.exceptions

sealed class AppointmentException(message: String): RuntimeException(message)

class AppointmentNotFoundException(message: String) : AppointmentException(message)

class AppointmentBadRequestException(message: String) : AppointmentException(message)

class AppointmentConflictIntegrityException(message: String) : AppointmentException(message)