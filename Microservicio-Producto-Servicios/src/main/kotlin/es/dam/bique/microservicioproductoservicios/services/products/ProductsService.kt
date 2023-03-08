package es.dam.bique.microservicioproductoservicios.services.products

import es.dam.bique.microservicioproductoservicios.exceptions.ProductNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.repositories.products.ProductsCachedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Service for products that implements the methods of ProductsRepository
 * @property productsRepository Product repository to access the database
 * @author The BiquesDAM Team
 */
@Service
class ProductsService
    @Autowired constructor(
    private val productsRepository: ProductsCachedRepository
): IProductsService{

    /**
     * Find all products
     * @return Flow of products
     * @author The BiquesDAM Team
     */
    override suspend fun findAll(): Flow<Product> {
        
        logger.info { "Service products - findAll()" }
        return productsRepository.findAll()    }

    suspend fun findByUuid(uuid: UUID): Product = withContext(Dispatchers.IO) {

        logger.info { "Service products - findByUuid() with uuid: $uuid" }

        return@withContext productsRepository.findByUuid(uuid)
            ?: throw ProductNotFoundException("Not found with uuid: $uuid")

    }

    override suspend fun findById(id: Long): Product {
        
        logger.info { "Service products - findById() with id: $id" }
        return productsRepository.findById(id)
            ?: throw ProductNotFoundException("Not found with id: $id")
    
    }

    override suspend fun save(product: Product): Product {

        logger.info { "Service products - save() product: $product" }
        return productsRepository.save(product)

    }

    override suspend fun update(product: Product): Product {

        logger.info { "Service products - update() product: $product" }

        val found = productsRepository.findByUuid(product.uuid)

        return found?.let {
            productsRepository.update(product)
        } ?: throw ProductNotFoundException("Not found with uuid: ${product.uuid}")

    }

    override suspend fun delete(product: Product): Product {

        logger.info { "Service appointments - delete() product: $product" }

        val found = productsRepository.findByUuid(product.uuid)

        found?.let {
            productsRepository.delete(found)
            return product
        } ?: throw ProductNotFoundException("Product not found with uuid: ${product.uuid}")

    }

}