package es.dam.bique.microservicioproductoservicios.dto

/**
 * Data Transfer Object for Service
 * @param id
 * @param uuid
 * @param image
 * @param price
 * @param appointment
 * @param type
 * @author The BiquesDAM Team
 */
data class ServiceDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val price: Float,
    val appointment : String,
    val type : String
)

/**
 * Data Transfer Object for Service
 * @param image
 * @param price
 * @param appointment
 * @param type
 * @author The BiquesDAM Team
 */
data class ServiceCreateDTO(
    val image: String,
    val price: Float,
    val appointment : String,
    val type : String
)