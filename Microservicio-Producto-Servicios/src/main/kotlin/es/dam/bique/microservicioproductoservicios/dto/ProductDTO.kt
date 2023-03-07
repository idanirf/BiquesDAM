package es.dam.bique.microservicioproductoservicios.dto

/**
 * Data Transfer Object for Product
 * @param id
 * @param uuid
 * @param image
 * @param brand
 * @param model
 * @param description
 * @param price
 * @param discountPercentage
 * @param stock
 * @param isAvailable
 * @param type
 * @author The BiquesDAM Team
 */
data class ProductDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val brand: String,
    val model: String,
    val description: String,
    val price: Float,
    val discountPercentage: Float,
    val stock : String,
    val isAvailable: Boolean,
    val type : String
)

/**
 * Data Transfer Object for Product
 * @param image
 * @param brand
 * @param model
 * @param description
 * @param price
 * @param discountPercentage
 * @param stock
 * @param isAvailable
 * @param type
 * @author The BiquesDAM Team
 */
data class ProductCreateDTO(
    val image: String,
    val brand: String,
    val model: String,
    val description: String,
    val price: Float,
    val discountPercentage: Float,
    val stock : String,
    val isAvailable: Boolean,
    val type : String
)