package es.dam.bique.microservicioproductoservicios.repositories.products

import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.repositories.CRUDRepository

interface IProductsCachedRepository : CRUDRepository<Product, Long> {
}