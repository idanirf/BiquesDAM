package es.dam.bique.microservicioproductoservicios.services.services

import es.dam.bique.microservicioproductoservicios.exceptions.ServiceNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Service
import es.dam.bique.microservicioproductoservicios.repositories.services.ServiceRepository
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired

private val logger = KotlinLogging.logger {}

@org.springframework.stereotype.Service
class ServicesService

    @Autowired
    constructor(
        private val serviceRepository : ServiceRepository
    ): IServicesService {


        override suspend fun findAll(): Flow<Service> {

            logger.info { "Service service - findAll()" }
            return serviceRepository.findAll()

        }

        override suspend fun findById(id: Long): Service {

            logger.info { "Service service - findById() with id: $id" }
            return serviceRepository.findById(id)
                ?: throw ServiceNotFoundException("Service not found with id: $id")

        }

        override suspend fun save(service: Service): Service {

            logger.info { "Service service - save() service: $service" }
            //TODO: ¿Queremos notificar el cambio al usuario?
            return serviceRepository.save(service)

        }

        override suspend fun update(service: Service): Service {

            logger.info { "Service service - update() service: $service" }
            val found = serviceRepository.findById(service.id.toString().toLong())

            found?.let {
                //TODO: ¿Queremos notificar el cambio al usuario?
                return serviceRepository.save(service)

            } ?: throw ServiceNotFoundException("Service not found with id: ${service.id}")

        }

        override suspend fun delete(service: Service): Service {

            logger.info { "Service service - delete() service: $service" }

            val found = serviceRepository.findById(service.id.toString().toLong())

            found?.let {
                //TODO: ¿Queremos notificar el cambio al usuario?
                return service
            } ?: throw ServiceNotFoundException("Service not found with id: ${service.id}")

        }
    }
