package es.dam.bique.microservicioproductoservicios.repositories.products

import es.dam.bique.microservicioproductoservicios.exceptions.ProductConflictIntegrityException
import es.dam.bique.microservicioproductoservicios.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Repository for products with cache
 * @param productsRepository repository for products
 * @author The BiquesDAM Team
 */
@Repository
class ProductsCachedRepository
    @Autowired constructor(
        private val productsRepository: ProductsRepository
    ) : IProductsCachedRepository {

    /**
     * Find all products
     * @return a flow of products
     * @author The BiquesDAM Team
     */
    override suspend fun findAll(): Flow<Product> = withContext(Dispatchers.IO) {

        logger.info { "Cached products - findAll()" }
        return@withContext productsRepository.findAll()

    }

    /**
     * Find product by id
     * @param id Product identifier
     * @return Product found
     * @author The BiquesDam Team
     */
    @Cacheable("products")
    override suspend fun findById(id: Long): Product? = withContext(Dispatchers.IO) {

        logger.info { "Cached products - findById() with id: $id" }
        return@withContext productsRepository.findById(id)

    }

    /**
     * Find product by uuid
     * @param uuid Product uuid
     * @return Product found
     */
    @Cacheable("products")
    suspend fun findByUuid(uuid: UUID): Product? = withContext(Dispatchers.IO) {

        logger.info { "Cached products - findByUuid() with id: $uuid" }
        return@withContext productsRepository.findByUuid(uuid).firstOrNull()

    }

    /**
     * Save product
     * @param entity Product to save
     * @return Product saved
     * @author The BiquesDam Team
     */
    @CachePut("products")
    override suspend fun save(entity: Product): Product = withContext(Dispatchers.IO) {

        logger.info { "Cached products - save() product: $entity" }
        return@withContext productsRepository.save(entity)

    }

    /**
     * Update product
     * @param entity Product to update
     * @return Product updated or null if not found
     */
    @CachePut("products")
    override suspend fun update(entity: Product): Product? = withContext(Dispatchers.IO) {
        logger.info { "Cached products - update() product: $entity" }
        val product = productsRepository.findByUuid(entity.uuid).firstOrNull()
        product?.let {
            val updated = it.copy(
                uuid = entity.uuid,
                image = entity.image,
                brand = entity.brand,
                model = entity.model,
                description = entity.description,
                price = entity.price,
                discountPercentage = entity.discountPercentage,
                stock = entity.stock,
                isAvailable = entity.isAvailable,
                type = entity.type
            )
            return@withContext productsRepository.save(updated)
        }
        return@withContext null
    }

    /**
     * Delete product
     * @param entity Product to delete
     * @return true if deleted, false if not found
     * @throws ProductConflictIntegrityException if product is in use by other entities
     * @author The BiquesDam Team
     */
    @CacheEvict("products")
    override suspend fun delete(entity: Product): Boolean = withContext(Dispatchers.IO) {

        logger.info { "Cached products - delete() product: $entity" }

        val product = productsRepository.findById(entity.id!!.toString().toLong())

        try {
            product?.let {
                productsRepository.deleteById(it.id!!.toString().toLong())
                return@withContext true
            }
        } catch (e: Exception) {
            throw ProductConflictIntegrityException("No se puede borrar el producto: $entity")
        }

        return@withContext false
    }
}