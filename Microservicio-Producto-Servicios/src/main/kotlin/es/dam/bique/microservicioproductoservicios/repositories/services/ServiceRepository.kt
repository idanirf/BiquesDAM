package es.dam.bique.microservicioproductoservicios.repositories.services

import es.dam.bique.microservicioproductoservicios.models.Service
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository : CoroutineCrudRepository<Service, Long>{

}