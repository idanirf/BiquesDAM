package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.*
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.models.ProductType
import es.dam.bique.microservicioproductoservicios.models.StockType
import java.util.*

/**
 * Creates a ProductDTO from a Product
 * @return ProductDTO
 * @author The BiquesDam Team
 */
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

/**
 * Creates an OnSaleDTO from a ProductDTO
 * @return OnSaleDTO
 * @author The BiquesDam Team
 */
fun ProductDTO.toOnSaleDTO(): OnSaleDTO {
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

/**
 * Creates a Product from a ProductCreateDTO
 * @return Product
 * @author The BiquesDam Team
 */
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

/**
 * Creates an OnSaleCreateDTO from a ProductCreateDTO
 * @return OnSaleCreateDTO
 * @author The BiquesDam Team
 */
fun ProductCreateDTO.toOnSaleCreateDTO(): OnSaleCreateDTO {
    return OnSaleCreateDTO(
        productEntity = ProductCreateDTO(
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
