package es.dam.bique.microservicioproductoservicios.dto

data class OnSaleDTO(
    val productEntity : ProductDTO?,
    val serviceEntity : ServiceDTO?,
    val type : OnSaleType
){
    enum class OnSaleType {
        PRODUCT, SERVICE
    }
}