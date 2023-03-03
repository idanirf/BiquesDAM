package es.dam.bique.microservicioproductoservicios.validators

import es.dam.bique.microservicioproductoservicios.dto.AppointmentCreateDTO
import es.dam.bique.microservicioproductoservicios.exceptions.ProductBadRequestException

fun AppointmentCreateDTO.validate(): AppointmentCreateDTO {

    if (this.userId.isBlank())
        throw ProductBadRequestException("The user cannot be empty.")
    if (this.assistance.isBlank())
        throw ProductBadRequestException("Assistance type cannot be empty.")
    if (this.date.isBlank())
        throw ProductBadRequestException("The date field cannot be empty.")

    return this
}