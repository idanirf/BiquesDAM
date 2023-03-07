package es.dam.bique.microservicioproductoservicios.services.products

import es.dam.bique.microservicioproductoservicios.models.Product
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the service that will manage the products
 * @author The BiquesDAM Team
 */
interface IProductsService {

    suspend fun findAll(): Flow<Product>
    suspend fun findById(id: Long): Product
    suspend fun save(product: Product): Product
    suspend fun update(product: Product): Product
    suspend fun delete(product: Product): Product

}