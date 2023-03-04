package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.*
import es.dam.bique.microservicioproductoservicios.models.Service
import es.dam.bique.microservicioproductoservicios.models.ServiceType

import java.util.*

fun ServiceDTO.toEntity(): Service {
    return Service(
        id = id,
        uuid = UUID.fromString(uuid),
        image = image,
        price = price,
        appointment = UUID.fromString(appointment),
        type = ServiceType.from(type)
    )
}

fun ServiceDTO.toOnSaleDTO (): OnSaleDTO {
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

fun Service.toDTO(): ServiceDTO{
    return ServiceDTO(
        id = id,
        uuid = uuid.toString(),
        image = image,
        price = price,
        appointment = appointment.toString(),
        type = type.value
    )
}

fun ServiceCreateDTO.toModel(uuid: UUID): Service {
    return Service(
        uuid = uuid,
        image = image,
        price = price,
        appointment = UUID.fromString(appointment),
        type = ServiceType.from(type)
    )
}

fun ServiceCreateDTO.toOnSaleCreateDTO (): OnSaleCreateDTO {
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