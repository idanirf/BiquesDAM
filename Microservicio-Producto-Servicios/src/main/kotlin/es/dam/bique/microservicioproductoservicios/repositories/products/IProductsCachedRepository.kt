package es.dam.bique.microservicioproductoservicios.repositories.products

import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.repositories.CRUDRepository

/**
 * Interface for the cached repository of products.
 * @author The BiquesDAM Team
 */
interface IProductsCachedRepository : CRUDRepository<Product, Long>