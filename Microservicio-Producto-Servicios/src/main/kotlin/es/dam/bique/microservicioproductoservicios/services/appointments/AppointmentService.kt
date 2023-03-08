package es.dam.bique.microservicioproductoservicios.services.appointments

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Service for appointments that implements the methods of AppointmentsRepository
 * @property appointmentsRepository
 * @author The BiquesDAM Team
 */
@Service
class AppointmentService
    @Autowired constructor(
        private val appointmentsRepository : AppointmentsCachedRepository
    ): IAppointmentsService {

    /**
     * Find all appointments
     * @return Flow of appointments
     * @author The BiquesDAM Team
     */
    override suspend fun findAll(): Flow<Appointment> {

        logger.info { "Service appointment - findAll()" }
        return appointmentsRepository.findAll()

    }

    /**
     * Find appointment by uuid
     * @param uuid Appointment uuid
     * @return Appointment found
     * @throws AppointmentNotFoundException if an appointment is not found with the indicated uuid
     * @author The BiquesDam Team
     */
    suspend fun findByUuid(uuid: UUID): Appointment = withContext(Dispatchers.IO) {
        logger.info { "Service appointments - findByUuid() with uuid: $uuid" }
        return@withContext appointmentsRepository.findByUuid(uuid)
            ?: throw AppointmentNotFoundException("Appointment not found with uuid: $uuid")

    }

    /**
     * Find appointment by id
     * @param id Appointment identifier
     * @return Appointment found
     * @throws AppointmentNotFoundException if an appointment is not found with the indicated id
     * @author The BiquesDam Team
     */
    override suspend fun findById(id: Long): Appointment {

        logger.info { "Service appointments - findById() with id: $id" }
        return appointmentsRepository.findById(id)
            ?: throw AppointmentNotFoundException("Appointment not found with id: $id")

    }

    /**
     * Save appointment
     * @param appointment Appointment to save
     * @return Appointment saved
     * @author The BiquesDam Team
     */
    override suspend fun save(appointment: Appointment): Appointment {

        logger.info { "Service appointments - save() appointment: $appointment" }
        return appointmentsRepository.save(appointment)

    }

    /**
     * Update appointment
     * @param appointment Appointment to update
     * @return Appointment updated
     * @throws AppointmentNotFoundException if an appointment is not found with the indicated uuid
     * @author The BiquesDam Team
     */
    override suspend fun update(appointment: Appointment): Appointment {

        logger.info { "Service appointments - update() appointment: $appointment" }

        val found = appointmentsRepository.findByUuid(appointment.uuid)

        return found?.let {
            appointmentsRepository.update(appointment)
        } ?: throw AppointmentNotFoundException("Appointment not found with uuid: ${appointment.uuid}")

    }

    /**
     * Delete appointment
     * @param appointment Appointment to delete
     * @return Appointment deleted
     * @throws AppointmentNotFoundException if an appointment is not found with the indicated uuid
     * @author The BiquesDam Team
     */
    override suspend fun delete(appointment: Appointment): Appointment {

        logger.info { "Service appointments - delete() product: $appointment" }

        val found = appointmentsRepository.findByUuid(appointment.uuid)

        found?.let {
            appointmentsRepository.delete(found)
            return appointment
        } ?: throw AppointmentNotFoundException("Appointment not found with uuid: ${appointment.uuid}")

    }
}