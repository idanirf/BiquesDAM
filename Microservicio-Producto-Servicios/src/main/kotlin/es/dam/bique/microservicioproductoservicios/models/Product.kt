package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Represents the products that are on sale.
 * @param id Product identifier.
 * @param uuid Product unique identifier.
 * @param image Product image.
 * @param brand Product brand.
 * @param model Product model.
 * @param description Product description.
 * @param price Product price.
 * @param discountPercentage Product discount percentage.
 * @param stock Product stock.
 * @param isAvailable Product availability.
 * @param type Product type.
 * @author The BiquesDam Team.
 */
@Serializable
@Table("Products")
data class Product(

    @Id
    override val id: Long? = null,

    @Serializable(with = UUIDSerializer::class)
    override val uuid: UUID,

    val image: String,

    val brand: String,

    val model: String,

    val description: String,

    val price: Float,

    val discountPercentage: Float,

    @Contextual
    val stock : StockType,

    val isAvailable: Boolean,

    @Contextual
    val type : ProductType,

    ): OnSale

/**
 * Represents the stock type.
 * @param value Value of the stock type.
 */
enum class StockType(val value: String){
    HIGH("HIGH"),
    LIMITED("LIMITED"),
    SOLDOUT("SOLDOUT");
    companion object {
        fun from(tipo: String): StockType {
            return when (tipo.uppercase()) {
                "HIGH" -> HIGH
                "LIMITED" -> LIMITED
                "SOLDOUT" -> SOLDOUT
                else -> throw IllegalArgumentException("Stock type unknown.")
            }
        }
    }
}

/**
 * Represents the product type.
 * @param value Value of the product type.
 */
enum class ProductType(val value: String){
    BIKES("BIKES"),
    E_BIKES("E_BIKES"),
    COMPONENTS("COMPONENTS"),
    EQUIPMENT("EQUIPMENT"),
    ACCESORIES("ACCESORIES");
    companion object {
        fun from(tipo: String): ProductType {
            return when (tipo.uppercase()) {
                "BIKES" -> BIKES
                "E_BIKES" -> E_BIKES
                "COMPONENTS" -> COMPONENTS
                "EQUIPMENT" -> EQUIPMENT
                "ACCESORIES" -> ACCESORIES
                else -> throw IllegalArgumentException("Product type unknown.")
            }
        }
    }
}