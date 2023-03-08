package es.dam.bique.microservicioproductoservicios.services.services

import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.models.Service
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Interface for the service that will manage the services
 * @author The BiquesDAM Team
 */
interface IServicesService {

    suspend fun findAll(): Flow<Service>
    suspend fun findById(id: Long): Service
    suspend fun save(service: Service): Service
    suspend fun update(service: Service): Service
    suspend fun delete(service: Service): Service
    suspend fun findAppointment(uuid: UUID): Appointment?

}
