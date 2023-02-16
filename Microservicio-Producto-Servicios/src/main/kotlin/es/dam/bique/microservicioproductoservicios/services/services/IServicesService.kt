package es.dam.bique.microservicioproductoservicios.services.services

import es.dam.bique.microservicioproductoservicios.models.Service
import kotlinx.coroutines.flow.Flow

interface IServicesService {
    suspend fun findAll(): Flow<Service>
    suspend fun findById(id: Long): Service
    suspend fun save(service: Service): Service
    suspend fun update(service: Service): Service
    suspend fun delete(service: Service): Service
}
