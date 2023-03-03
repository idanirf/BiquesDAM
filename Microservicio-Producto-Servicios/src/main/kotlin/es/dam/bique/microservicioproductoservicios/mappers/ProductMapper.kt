package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.*
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
            id = id,
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
        type = OnSaleType.PRODUCT
    )
}

fun Product.toDTO(): ProductDTO{
    return ProductDTO(
        id = id,
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

fun ProductCreateDTO.toModel(uuid : UUID): Product{
    return Product(
        uuid = uuid,
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
