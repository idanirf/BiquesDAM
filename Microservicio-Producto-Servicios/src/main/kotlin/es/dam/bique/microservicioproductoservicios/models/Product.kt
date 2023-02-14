package es.dam.bique.microservicioproductoservicios.models

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "PRODUCTS")
data class Product(
    @Id
    val id: Long? = null,

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

    @NotNull(message = "Stock cannot be empty.")
    val stock : StockType,

    @NotNull(message = "Availability cannot be empty.")
    val isAvailable: Boolean,

    @NotNull(message = "The Product Type cannot be empty.")
    val type : ProductType

    ): OnSale

enum class StockType{
    HIGH, LIMITED, SOLDOUT
}

enum class ProductType{
    BIKES, E_BIKES, COMPONENTS, EQUIPMENT, ACCESORIES
}