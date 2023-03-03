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
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

private val logger = KotlinLogging.logger {}

@Repository
class ProductsCachedRepository
    @Autowired constructor(
        private val productsRepository: ProductsRepository
    ) : IProductsCachedRepository {

    override suspend fun findAll(): Flow<Product> = withContext(Dispatchers.IO) {

        logger.info { "Cached products - findAll()" }
        return@withContext productsRepository.findAll()

    }

    @Cacheable("products")
    override suspend fun findById(id: Long): Product? = withContext(Dispatchers.IO) {

        logger.info { "Cached products - findById() with id: $id" }
        return@withContext productsRepository.findById(id)

    }

    @Cacheable("products")
    suspend fun findByUuid(uuid: UUID): Product? = withContext(Dispatchers.IO) {

        logger.info { "Cached products - findByUuid() with id: $uuid" }
        return@withContext productsRepository.findByUuid(uuid).firstOrNull()

    }

    @CachePut("products")
    override suspend fun save(entity: Product): Product = withContext(Dispatchers.IO) {

        logger.info { "Cached products - save() product: $entity" }
        return@withContext productsRepository.save(entity)

    }

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