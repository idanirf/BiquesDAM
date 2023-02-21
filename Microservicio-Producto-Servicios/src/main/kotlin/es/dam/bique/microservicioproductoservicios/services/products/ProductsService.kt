package es.dam.bique.microservicioproductoservicios.services.products

import es.dam.bique.microservicioproductoservicios.exceptions.ProductNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.repositories.products.ProductsCachedRepository
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ProductsService
    @Autowired constructor(
    private val productsRepository: ProductsCachedRepository
): IProductsService{
    override suspend fun findAll(): Flow<Product> {
        
        logger.info { "Service products - findAll()" }
        return productsRepository.findAll()    }

    override suspend fun findById(id: Long): Product {
        
        logger.info { "Service products - findById() with id: $id" }
        return productsRepository.findById(id)
            ?: throw ProductNotFoundException("Product not found with id: $id")    
    
    }

    override suspend fun save(product: Product): Product {

        logger.info { "Service products - save() product: $product" }
        //TODO: ¿Queremos notificar el cambio al usuario?
        return productsRepository.save(product)

    }

    override suspend fun update(product: Product): Product {

        logger.info { "Service products - update() product: $product" }

        val found = productsRepository.findById(product.id.toString().toLong())

        found?.let {
            //TODO: ¿Queremos notificar el cambio al usuario?
            return productsRepository.update(product)!!

        } ?: throw ProductNotFoundException("Product not found with id: ${product.id}")

    }

    override suspend fun delete(product: Product): Product {

        logger.info { "Service appointments - delete() product: $product" }

        val found = productsRepository.findById(product.id.toString().toLong())

        found?.let {
            //TODO: ¿Queremos notificar el cambio al usuario?
            return product
        } ?: throw ProductNotFoundException("Product not found with id: ${product.id}")

    }

}