package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class SaleDTO(
    val productEntity: ProductDTO?,
    val serviceEntity: ServiceDTO?,
    val type: String?
)

@Serializable
data class SaleCreateDTO(
    val productEntity: ProductCreateDTO?,
    val serviceEntity: ServiceCreateDTO?,
    val type: String?
)

@Serializable
data class FinalSaleDTO(
    val productEntity: ProductDTO?,
    val serviceEntity: FinalServiceDTO?,
    val type: String?
)

data class AllSaleDTO(
    val data: List<SaleDTO>
)

