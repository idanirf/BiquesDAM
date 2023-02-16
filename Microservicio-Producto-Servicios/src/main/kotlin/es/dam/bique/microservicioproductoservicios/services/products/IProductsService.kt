package es.dam.bique.microservicioproductoservicios.services.products

import es.dam.bique.microservicioproductoservicios.models.Product
import kotlinx.coroutines.flow.Flow

interface IProductsService {
    suspend fun findAll(): Flow<Product>
    suspend fun findById(id: Long): Product
    suspend fun save(product: Product): Product
    suspend fun update(product: Product): Product
    suspend fun delete(product: Product): Product
}