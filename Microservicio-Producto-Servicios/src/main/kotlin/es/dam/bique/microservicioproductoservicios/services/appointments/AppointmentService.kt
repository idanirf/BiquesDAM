package es.dam.bique.microservicioproductoservicios.services.appointments

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

        found?.let {
            //TODO: ¿Queremos notificar el cambio al usuario?
            return appointmentsRepository.update(appointment)!!

        } ?: throw AppointmentNotFoundException("Appointment not found with id: ${appointment.id}")

    }

    override suspend fun delete(appointment: Appointment): Appointment {

        logger.info { "Service appointments - delete() product: $appointment" }

        val found = appointmentsRepository.findById(appointment.id.toString().toLong())

        found?.let {
            //TODO: ¿Queremos notificar el cambio al usuario?
            return appointment
        } ?: throw AppointmentNotFoundException("Appointment not found with id: ${appointment.id}")

    }
}