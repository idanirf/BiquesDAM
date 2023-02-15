package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.ProductDTO
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.models.ProductType
import es.dam.bique.microservicioproductoservicios.models.StockType
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.util.*

fun ProductDTO.toEntity (): Product {
    return Product(
        id = ObjectId(id.toString()).toId(),
        uuid = UUID.fromString(uuid),
        image = image,
        brand = brand,
        model = model,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        stock = StockType.from(stock),
        isAvailable = isAvailable,
        type = ProductType.from(type)
    )
}

fun Product.toDTO(): ProductDTO{
    return ProductDTO(
        id = id.toString().toLong(),
        uuid = uuid.toString(),
        image = image,
        brand = brand,
        model = model,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        stock = stock.value,
        isAvailable = isAvailable,
        type = type.value
    )
}
