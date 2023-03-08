package es.dam.bique.microservicioproductoservicios.services.services

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.exceptions.ServiceNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.models.Service
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import es.dam.bique.microservicioproductoservicios.repositories.services.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Service that will manage the services
 * @property serviceRepository Repository that will manage the services
 * @property appointmentRepository Repository that will manage the appointments
 * @author The BiquesDAM Team
 */
@org.springframework.stereotype.Service
class ServicesService
    @Autowired
    constructor(
        private val serviceRepository : ServiceRepository,
        private val appointmentRepository: AppointmentsCachedRepository
    ): IServicesService {

        /**
         * Find all services
         * @return Flow of services
         * @author The BiquesDAM Team
         */
        override suspend fun findAll(): Flow<Service> {

            logger.info { "Service service - findAll()" }
            return serviceRepository.findAll()

        }

        /**
         * Find a service by its uuid
         * @param uuid UUID of the service
         * @return Service with the uuid given
         * @throws ServiceNotFoundException if the service is not found
         * @author The BiquesDAM Team
         */
        suspend fun findByUuid(uuid: UUID): Service = withContext(Dispatchers.IO) {

            logger.info { "Service service - findByUuid() with uuid: $uuid" }
            return@withContext serviceRepository.findByUuid(uuid).firstOrNull()
                ?: throw ServiceNotFoundException("Not found with uuid: $uuid")

        }

        /**
         * Find a service by its id
         * @param id Id of the service
         * @return Service with the id given
         * @throws ServiceNotFoundException if the service is not found
         * @author The BiquesDAM Team
         */
        override suspend fun findById(id: Long): Service {

            logger.info { "Service service - findById() with id: $id" }
            return serviceRepository.findById(id)
                ?: throw ServiceNotFoundException("Not found with id: $id")

        }

        /**
         * Find an appointment by its uuid
         * @param uuid UUID of the appointment
         * @return Appointment with the uuid given
         * @throws AppointmentNotFoundException if the appointment is not found
         * @author The BiquesDAM Team
         */
        override suspend fun findAppointment(uuid: UUID): Appointment? {
            logger.debug { "Service service - findAppointment() with uuid: $uuid" }

            return appointmentRepository.findByUuid(uuid)
                ?: throw AppointmentNotFoundException("Appointment not found with uuid: $uuid")

        }

        /**
         * Save a service
         * @param service Service to save
         * @return Service saved
         * @throws AppointmentNotFoundException if the appointment is not found
         * @author The BiquesDAM Team
         */
            override suspend fun save(service: Service): Service {

            logger.info { "Service service - save() service: $service" }
            val appointment = findAppointment(service.appointment)
            if(appointment != null) {
                return serviceRepository.save(service)
            }else{
                throw AppointmentNotFoundException("Appointment not found with uuid: ${service.appointment}")
            }

        }

        /**
         * Update a service
         * @param service Service to update
         * @return Service updated
         * @throws ServiceNotFoundException if the service is not found
         * @throws AppointmentNotFoundException if the appointment is not found
         * @author The BiquesDAM Team
         */
        override suspend fun update(service: Service): Service {

            logger.info { "Service service - update() service: $service" }
            val found = serviceRepository.findByUuid(service.uuid).firstOrNull()

            return if(found!=null){
                val appointment = findAppointment(service.appointment)
                if(appointment != null) {
                    serviceRepository.save(found)
                }else{
                    throw AppointmentNotFoundException("Appointment not found with uuid: ${service.appointment}")
                }
            }else{
                throw ServiceNotFoundException("Not found with uuid: ${service.uuid}")
            }

        }

        /**
         * Delete a service
         * @param service Service to delete
         * @return Service deleted
         * @throws ServiceNotFoundException if the service is not found
         * @throws AppointmentNotFoundException if the appointment is not found
         * @author The BiquesDAM Team
         */
        override suspend fun delete(service: Service): Service {

            logger.info { "Service service - delete() service: $service" }

            val found = serviceRepository.findByUuid(service.uuid).firstOrNull()

            found?.let {
                serviceRepository.delete(found)
                return service
            } ?: throw ServiceNotFoundException("Service not found with uuid: ${service.uuid}")

        }
    }
