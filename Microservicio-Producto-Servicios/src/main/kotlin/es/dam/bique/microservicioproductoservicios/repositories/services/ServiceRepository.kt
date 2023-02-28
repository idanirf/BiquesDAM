package es.dam.bique.microservicioproductoservicios.repositories.services

import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.models.Service
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ServiceRepository : CoroutineCrudRepository<Service, Long>{
    suspend fun findByUuid(uuid: UUID): Flow<Service>


}