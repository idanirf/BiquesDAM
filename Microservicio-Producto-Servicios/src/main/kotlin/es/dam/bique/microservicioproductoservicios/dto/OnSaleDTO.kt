package es.dam.bique.microservicioproductoservicios.dto

data class OnSaleDTO(
    val productEntity : ProductDTO?,
    val serviceEntity : ServiceDTO?,
    val type : OnSaleType
)

data class OnSaleCreateDTO(
    val productEntity : ProductCreateDTO?,
    val serviceEntity : ServiceCreateDTO?,
    val type : OnSaleType
)

enum class OnSaleType(val value:String) {
    PRODUCT("PRODUCT"),
    SERVICE("SERVICE")
}

data class AllSaleDTO(
    val data: List<OnSaleDTO>
)