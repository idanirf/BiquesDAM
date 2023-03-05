package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class SaleDTO(
    val productEntity : ProductDTO?,
    val serviceEntity : ServiceDTO?,
    val type : String
)

@Serializable
data class FinalSaleDTO(
    val productEntity : ProductDTO?,
    val serviceEntity : FinalServiceDTO?,
    val type : String
)

