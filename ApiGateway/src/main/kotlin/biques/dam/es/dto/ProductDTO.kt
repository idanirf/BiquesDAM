package biques.dam.es.dto

data class ProductDTO(
    val id: Long?,
    val uuid: String,
    val image: String,
    val brand: String,
    val model: String,
    val description: String,
    val price: Float,
    val discountPercentage: Float,
    val stock : String,
    val isAvailable: Boolean,
    val type : String
)

data class ProductCreateDTO(

    val image: String,
    val brand: String,
    val model: String,
    val description: String,
    val price: Float,
    val discountPercentage: Float,
    val stock : String,
    val isAvailable: Boolean,
    val type : String
)

