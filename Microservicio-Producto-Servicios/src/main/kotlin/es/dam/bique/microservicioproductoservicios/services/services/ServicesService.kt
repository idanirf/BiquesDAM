package es.dam.bique.microservicioproductoservicios.services.services

import es.dam.bique.microservicioproductoservicios.exceptions.ProductNotFoundException
import es.dam.bique.microservicioproductoservicios.exceptions.ServiceNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.models.Service
import es.dam.bique.microservicioproductoservicios.repositories.services.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

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

        suspend fun findByUuid(uuid: UUID): Service = withContext(Dispatchers.IO) {
            logger.info { "Service service - findByUuid() with uuid: $uuid" }
            return@withContext serviceRepository.findByUuid(uuid).firstOrNull()
                ?: throw ServiceNotFoundException("Not found with uuid: $uuid")

        }

        override suspend fun findById(id: Long): Service {

            logger.info { "Service service - findById() with id: $id" }
            return serviceRepository.findById(id)
                ?: throw ServiceNotFoundException("Not found with id: $id")

        }

        override suspend fun save(service: Service): Service {

            logger.info { "Service service - save() service: $service" }
            //TODO: ¿Queremos notificar el cambio al usuario?
            return serviceRepository.save(service)

        }

        override suspend fun update(service: Service): Service {

            logger.info { "Service service - update() service: $service" }
            val found = serviceRepository.findByUuid(service.uuid).firstOrNull()

            //TODO: ¿Queremos notificar el cambio al usuario?
            return if(found!=null){
                serviceRepository.save(found)
            }else{
                throw ServiceNotFoundException("Not found with uuid: ${service.uuid}")
            }

        }

        override suspend fun delete(service: Service): Service {

            logger.info { "Service service - delete() service: $service" }

            val found = serviceRepository.findByUuid(service.uuid).firstOrNull()

            found?.let {
                //TODO: ¿Queremos notificar el cambio al usuario?
                serviceRepository.delete(found)
                return service
            } ?: throw ServiceNotFoundException("Service not found with uuid: ${service.uuid}")

        }
    }
