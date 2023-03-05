package biques.dam.es.dto

data class SaleDTO(
    val productEntity : ProductDTO?,
    val serviceEntity : ServiceDTO?,
    val type : SaleType
)

enum class SaleType(val value:String) {
    PRODUCT("PRODUCT"),
    SERVICE("SERVICE")
}