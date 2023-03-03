package es.dam.bique.microservicioproductoservicios.services.appointments

import es.dam.bique.microservicioproductoservicios.dto.AppointmentUserDTO
import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}


@Service
class AppointmentService
    @Autowired constructor(
        private val appointmentsRepository : AppointmentsCachedRepository
    ): IAppointmentsService {

    override suspend fun findAll(): Flow<Appointment> {

        logger.info { "Service appointment - findAll()" }
        return appointmentsRepository.findAll()

    }

    /*override suspend fun findUser(id: UUID): AppointmentUserDTO {

        logger.debug { "Service appointment - findUser()" }

        val user = appointmentsRepository
            .findAll()
            .toList()
            .filter{it.user == id}
            .map { it.user }

        if(user.size == 1){
            return user[0]
        } else{
            throw AppointmentNotFoundException("User not found with id: $id")
        }

    }

     */

    suspend fun findByUuid(uuid: UUID): Appointment = withContext(Dispatchers.IO) {
        logger.info { "Service appointments - findByUuid() with uuid: $uuid" }
        return@withContext appointmentsRepository.findByUuid(uuid)
            ?: throw AppointmentNotFoundException("Appointment not found with uuid: $uuid")

    }

    override suspend fun findById(id: Long): Appointment {

        logger.info { "Service appointments - findById() with id: $id" }
        return appointmentsRepository.findById(id)
            ?: throw AppointmentNotFoundException("Appointment not found with id: $id")

    }

    override suspend fun save(appointment: Appointment): Appointment {

        logger.info { "Service appointments - save() appointment: $appointment" }
        //TODO: ¿Queremos notificar el cambio al usuario?
        return appointmentsRepository.save(appointment)

    }

    override suspend fun update(appointment: Appointment): Appointment {

        logger.info { "Service appointments - update() appointment: $appointment" }

        val found = appointmentsRepository.findByUuid(appointment.uuid)

            //TODO: ¿Queremos notificar el cambio al usuario?
        return found?.let {
            appointmentsRepository.update(appointment)
        } ?: throw AppointmentNotFoundException("Appointment not found with uuid: ${appointment.uuid}")

    }

    override suspend fun delete(appointment: Appointment): Appointment {

        logger.info { "Service appointments - delete() product: $appointment" }

        val found = appointmentsRepository.findByUuid(appointment.uuid)

        found?.let {
            //TODO: ¿Queremos notificar el cambio al usuario?
            appointmentsRepository.delete(found)
            return appointment
        } ?: throw AppointmentNotFoundException("Appointment not found with uuid: ${appointment.id}")

    }
}