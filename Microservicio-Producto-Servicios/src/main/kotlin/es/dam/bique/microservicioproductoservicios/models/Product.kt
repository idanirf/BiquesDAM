package es.dam.bique.microservicioproductoservicios.models

import es.dam.bique.microservicioproductoservicios.serializers.UUIDSerializer
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import org.litote.kmongo.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "PRODUCTS")
data class Product(

    @BsonId @Contextual
    val id: Id<Product> = newId(),

    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),

    @NotEmpty(message = "The image cannot be empty.")
    val image: String,

    @NotEmpty(message = "The brand cannot be empty.")
    val brand: String,

    @NotEmpty(message = "The model cannot be empty.")
    val model: String,

    @NotEmpty(message = "The description cannot be empty.")
    val description: String,

    @Min(value = 0, message = "The price cannot be negative.")
    val price: Float,

    @NotNull(message = "The percentage cannot be empty.")
    val discountPercentage: Float,

    @Contextual
    @NotNull(message = "Stock cannot be empty.")
    val stock : StockType,

    @NotNull(message = "Availability cannot be empty.")
    val isAvailable: Boolean,

    @Contextual
    @NotNull(message = "The Product Type cannot be empty.")
    val type : ProductType

    ): OnSale

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

enum class ProductType(val value: String){
    BIKES("BIKES"),
    E_BIKES("E_BIKES"),
    COMPONENTS("COMPONENTS"),
    EQUIPMENT("EQUIPMENT"),
    ACCESORIES("ACCESORIES");
    companion object {
        fun from(tipo: String): ProductType {
            return when (tipo.uppercase()) {
                "BIKES" -> ProductType.BIKES
                "E_BIKES" -> ProductType.E_BIKES
                "COMPONENTS" -> ProductType.COMPONENTS
                "EQUIPMENT" -> ProductType.EQUIPMENT
                "ACCESORIES" -> ProductType.ACCESORIES
                else -> throw IllegalArgumentException("Product type unknown.")
            }
        }
    }
}