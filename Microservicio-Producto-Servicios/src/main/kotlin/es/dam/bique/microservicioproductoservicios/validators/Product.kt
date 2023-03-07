package es.dam.bique.microservicioproductoservicios.validators

import es.dam.bique.microservicioproductoservicios.dto.ProductCreateDTO
import es.dam.bique.microservicioproductoservicios.exceptions.ProductBadRequestException

/**
 * Method that validates the fields of the ProductCreateDTO
 * @param productCreateDTO ProductCreateDTO to validate
 * @return ProductCreateDTO validated
 * @author The BiquesDAM Team
 */
fun ProductCreateDTO.validate(): ProductCreateDTO {

    if (this.image.isBlank())
        throw ProductBadRequestException("The image cannot be empty.")
    if (this.brand.isBlank())
        throw ProductBadRequestException("The brand cannot be empty.")
    if (this.model.isBlank())
        throw ProductBadRequestException("The model cannot be empty.")
    if (this.description.isBlank())
        throw ProductBadRequestException("The description cannot be empty.")
    if (this.price < 0.0)
        throw ProductBadRequestException("The percentage cannot be negative.")
    if (this.stock.isBlank())
        throw ProductBadRequestException("Stock cannot be empty.")
    if (this.isAvailable.toString().isBlank())
        throw ProductBadRequestException("Availability cannot be empty.")
    if (this.type.isBlank())
        throw ProductBadRequestException("The Product Type cannot be empty.")

    return this
}