package biques.dam.es.dto

import kotlinx.serialization.Serializable
/**
 * Data class representing a sale.
 * @property productEntity the product entity, nullable
 * @property serviceEntity the service entity, nullable
 * @property type the type of the sale, nullable
 */
@Serializable
data class SaleDTO(
    val productEntity: ProductDTO?,
    val serviceEntity: ServiceDTO?,
    val type: String?
)
/**
 * This data class represents a create request for a Sale.
 * @property productEntity the product entity, nullable
 * @property serviceEntity the service entity, nullable
 * @property type the type of the sale, nullable
 */
@Serializable
data class SaleCreateDTO(
    val productEntity: ProductCreateDTO?,
    val serviceEntity: ServiceCreateDTO?,
    val type: String?
)

/**
 * This data class represents a update request for a Sale.
 * @property productEntity the product entity, nullable
 * @property serviceEntity the service entity, nullable
 * @property type the type of the sale, nullable
 */
@Serializable
data class FinalSaleDTO(
    val productEntity: ProductDTO?,
    val serviceEntity: FinalServiceDTO?,
    val type: String?
)
/**
 * This data class represents a DTO for a list of Sales.
 * @param data a list of OrderDTO objects representing the orders.
 * @author BiquesDAM-Team
 */
data class AllSaleDTO(
    val data: List<SaleDTO>
)

