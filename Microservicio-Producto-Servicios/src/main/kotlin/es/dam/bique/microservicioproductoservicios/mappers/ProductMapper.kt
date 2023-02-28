package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.OnSaleDTO
import es.dam.bique.microservicioproductoservicios.dto.ProductCreateDTO
import es.dam.bique.microservicioproductoservicios.dto.ProductDTO
import es.dam.bique.microservicioproductoservicios.dto.ServiceDTO
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.models.ProductType
import es.dam.bique.microservicioproductoservicios.models.StockType
import java.util.*

fun ProductDTO.toEntity (): Product {
    return Product(
        id = id,
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

fun ProductDTO.toOnSaleDTO (): OnSaleDTO {
    return OnSaleDTO(
        productEntity = ProductDTO(
            id = id.toString().toLong(),
            uuid = uuid,
            image = image,
            brand = brand,
            model = model,
            description = description,
            price = price,
            discountPercentage = discountPercentage,
            stock = stock,
            isAvailable = isAvailable,
            type = type
        ),
        serviceEntity = null,
        type = OnSaleDTO.OnSaleType.PRODUCT
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

fun ProductCreateDTO.toModel(): Product{
    return Product(
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
