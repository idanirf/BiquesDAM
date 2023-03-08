package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentConflictIntegrityException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Cached repository for Appointment
 * @param appointmentsRepository Appointment repository
 * @author The BiquesDam Team
 */
@Repository
class AppointmentsCachedRepository
    @Autowired constructor(
        private val appointmentsRepository: AppointmentsRepository
    ) : IAppointmentsCachedRepository {

    /**
     * Find all appointments
     * @return Flow of appointments found
     * @author The BiquesDam Team
     */
    override suspend fun findAll(): Flow<Appointment> = withContext(Dispatchers.IO) {

        logger.info { "Cached appointment - findAll()" }
        return@withContext appointmentsRepository.findAll()

    }

    /**
     * Find appointment by id
     * @param id Appointment identifier
     * @return Appointment found
     * @author The BiquesDam Team
     */
    @Cacheable("appointments")
    override suspend fun findById(id: Long): Appointment? = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - findById() with id: $id" }
        return@withContext appointmentsRepository.findById(id)

    }

    /**
     * Find appointment by uuid
     * @param uuid Appointment uuid
     * @return Appointment found
     * @author The BiquesDam Team
     */
    @Cacheable("appointments")
    suspend fun findByUuid(uuid: UUID): Appointment? = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - findByUuid() with id: $uuid" }
        return@withContext appointmentsRepository.findByUuid(uuid).firstOrNull()

    }

    /**
     * Save appointment
     * @param entity Appointment to save
     * @return Appointment saved
     * @author The BiquesDam Team
     */
    @CachePut("appointments")
    override suspend fun save(entity: Appointment): Appointment = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - save() appointment: $entity" }
        return@withContext appointmentsRepository.save(entity)

    }

    /**
     * Update appointment
     * @param entity Appointment to update
     * @return An updated Appointment
     * @author The BiquesDam Team
     */
    @CachePut("appointments")
    override suspend fun update(entity: Appointment): Appointment? = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - update() appointment: $entity" }
        val appointment = appointmentsRepository.findByUuid(entity.uuid).firstOrNull()
        appointment?.let{
            val updated = it.copy(
                uuid = entity.uuid,
                userId = entity.userId,
                assistance = entity.assistance,
                date = entity.date,
                description = entity.description,
            )
            return@withContext appointmentsRepository.save(updated)
        }
        return@withContext null

    }

    /**
     * Delete appointment
     * @param entity Appointment to delete
     * @return Boolean indicating if the appointment has been deleted
     * @throws AppointmentConflictIntegrityException if the appointment cannot be deleted
     * @author The BiquesDam Team
     */
    @CacheEvict("appointments")
    override suspend fun delete(entity: Appointment): Boolean = withContext(Dispatchers.IO) {

        logger.info { "Cached appointment - delete() product: $entity" }
        val appointment = entity.id?.let { appointmentsRepository.findById(it) }
        try {
            appointment?.let {
                appointmentsRepository.deleteById(it.id!!.toString().toLong())
                return@withContext true
            }
        } catch (e: Exception) {
            throw AppointmentConflictIntegrityException("No se puede borrar el appointment: $entity")
        }
        return@withContext false

    }
}