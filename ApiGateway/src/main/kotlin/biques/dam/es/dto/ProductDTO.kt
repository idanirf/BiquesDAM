package biques.dam.es.dto

import kotlinx.serialization.Serializable

/**
 * This data class represents a DTO for the Product model.
 * @param id a long representing the id of the appointment.
 * @param uuid a string representing the uuid of the appointment.
 * @param user a string representing the user of the appointment.
 * @param assistance a string representing the assistance of the appointment.
 * @param date a string representing the date of the appointment.
 * @param description a string representing the description of the appointment.
 * @author BiquesDAM-Team
 */
@Serializable
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
 * This data class represents a create request for a Product.
 * @param image The product image.
 * @param brand The product brand.
 * @param model The product model.
 * @param description The product description.
 * @param price The product price.
 * @param discountPercentage The product discount percentage.
 * @param stock The product stock.
 * @param isAvailable Whether the product is available or not.
 * @param type The product type.
 */
@Serializable
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

