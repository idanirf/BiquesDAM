package es.dam.bique.microservicioproductoservicios.validators

import es.dam.bique.microservicioproductoservicios.dto.ServiceCreateDTO
import es.dam.bique.microservicioproductoservicios.exceptions.ServiceBadRequestException

/**
 * Method that validates the fields of the ServiceCreateDTO
 * @param serviceCreateDTO ServiceCreateDTO to validate
 * @return ServiceCreateDTO validated
 * @author The BiquesDAM Team
 */
fun ServiceCreateDTO.validate(): ServiceCreateDTO {

    if (this.image.isBlank()) {
        throw ServiceBadRequestException("The image cannot be empty.")
    } else if (this.price <= 0) {
        throw ServiceBadRequestException("The price cannot be negative.")
    } else if (this.type.isBlank()) {
        throw ServiceBadRequestException("The type cannot be empty.")
    }

    return this
}