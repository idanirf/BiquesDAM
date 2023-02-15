package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentConflictIntegrityException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository


private val logger = KotlinLogging.logger {}

@Repository
class AppointmentsCachedRepository

    @Autowired constructor(
        private val appointmentsRepository: AppointmentsRepository
    ) : IAppointmentsCachedRepository {

    override suspend fun findAll(): Flow<Appointment> = withContext(Dispatchers.IO) {

        logger.info { "Cached appointment - findAll()" }
        return@withContext appointmentsRepository.findAll()

    }

    @Cacheable("appointments")
    override suspend fun findById(id: Long): Appointment? = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - findById() with id: $id" }
        return@withContext appointmentsRepository.findById(id)

    }

    @CachePut("appointments")
    override suspend fun save(entity: Appointment): Appointment = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - save() appointment: $entity" }
        return@withContext appointmentsRepository.save(entity)

    }

    @CachePut("appointments")
    override suspend fun update(entity: Appointment): Appointment? = withContext(Dispatchers.IO) {

        logger.info { "Cached appointments - update() appointment: $entity" }
        val appointment = appointmentsRepository.findById(entity.id!!.toString().toLong())
        appointment?.let{
            val updated = it.copy(
                user = entity.user,
                assistance = entity.assistance,
                date = entity.date,
                description = entity.description,
            )
            return@withContext appointmentsRepository.save(updated)
        }

        return@withContext null
    }

    @CacheEvict("appointments")
    override suspend fun delete(entity: Appointment): Boolean = withContext(Dispatchers.IO) {

        logger.info { "Cached appointment - delete() product: $entity" }
        val appointment = appointmentsRepository.findById(entity.id!!.toString().toLong())
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