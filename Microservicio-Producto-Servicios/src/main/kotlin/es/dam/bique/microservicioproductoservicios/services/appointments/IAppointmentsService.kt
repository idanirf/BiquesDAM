package es.dam.bique.microservicioproductoservicios.services.appointments

import es.dam.bique.microservicioproductoservicios.models.Appointment
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the service that will manage the appointments
 * @author The BiquesDAM Team
 */
interface IAppointmentsService {

    suspend fun findAll(): Flow<Appointment>
    suspend fun findById(id: Long): Appointment
    suspend fun save(appointment: Appointment): Appointment
    suspend fun update(appointment: Appointment): Appointment
    suspend fun delete(appointment: Appointment): Appointment

}