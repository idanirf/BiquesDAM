package es.dam.bique.microservicioproductoservicios.dto

/**
 * Data Transfer Object for OnSale
 * @param productEntity OnSale product
 * @param serviceEntity OnSale service
 * @param type OnSale type
 * @author The BiquesDam Team
 */
data class OnSaleDTO(
    val productEntity : ProductDTO?,
    val serviceEntity : ServiceDTO?,
    val type : OnSaleType
)

/**
 * Data Transfer Object for OnSaleCreate
 * @param productEntity OnSale product
 * @param serviceEntity OnSale service
 * @param type OnSale type
 * @author The BiquesDam Team
 */
data class OnSaleCreateDTO(
    val productEntity : ProductCreateDTO?,
    val serviceEntity : ServiceCreateDTO?,
    val type : OnSaleType
)

/**
 * Represents the type of OnSale.
 * @param value Value of the OnSale type.
 */
enum class OnSaleType(val value:String) {
    PRODUCT("PRODUCT"),
    SERVICE("SERVICE")
}