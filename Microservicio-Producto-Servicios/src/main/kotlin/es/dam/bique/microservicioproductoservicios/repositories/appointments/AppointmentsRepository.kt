package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.models.Appointment
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for Appointment
 * @author The BiquesDam Team
 */
@Repository
interface AppointmentsRepository : CoroutineCrudRepository<Appointment, Long> {

    suspend fun findByUuid(uuid: UUID): Flow<Appointment>

}