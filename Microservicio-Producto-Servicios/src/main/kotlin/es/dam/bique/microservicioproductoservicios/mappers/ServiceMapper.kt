package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.*
import es.dam.bique.microservicioproductoservicios.models.Service
import es.dam.bique.microservicioproductoservicios.models.ServiceType

import java.util.*

/**
 * Creates a ServiceDTO from a Service
 * @return ServiceDTO
 * @author The BiquesDam Team
 */
fun Service.toDTO(): ServiceDTO{
    return ServiceDTO(
        id = id,
        uuid = uuid.toString(),
        image = image,
        price = price,
        appointment = appointment.toString(),
        type = type.type
    )
}

/**
 * Creates a Service from a ServiceDTO
 * @return Service
 * @author The BiquesDam Team
 */
fun ServiceDTO.toOnSaleDTO(): OnSaleDTO {
    return OnSaleDTO(
        productEntity = null,
        serviceEntity = ServiceDTO(
            id = id,
            uuid = uuid,
            image = image,
            price = price,
            appointment = appointment,
            type = type ),
        type = OnSaleType.SERVICE
    )
}

/**
 * Creates a Service from a ServiceCreateDTO
 * @return Service
 * @author The BiquesDam Team
 */
fun ServiceCreateDTO.toModel(uuid: UUID): Service {
    return Service(
        uuid = uuid,
        image = image,
        price = price,
        appointment = UUID.fromString(appointment),
        type = ServiceType.from(type)
    )
}

/**
 * Creates an OnSaleCreateDTO from a Service
 * @return ServiceCreateDTO
 * @author The BiquesDam Team
 */
fun ServiceCreateDTO.toOnSaleCreateDTO(): OnSaleCreateDTO {
    return OnSaleCreateDTO(
        productEntity = null,
        serviceEntity = ServiceCreateDTO(
            image = image,
            price = price,
            appointment = appointment,
            type = type ),
        type = OnSaleType.SERVICE
    )
}