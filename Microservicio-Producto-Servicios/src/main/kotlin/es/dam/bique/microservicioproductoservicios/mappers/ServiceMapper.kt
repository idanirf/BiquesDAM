package es.dam.bique.microservicioproductoservicios.mappers

import es.dam.bique.microservicioproductoservicios.dto.OnSaleDTO
import es.dam.bique.microservicioproductoservicios.dto.ProductDTO
import es.dam.bique.microservicioproductoservicios.dto.ServiceCreateDTO
import es.dam.bique.microservicioproductoservicios.dto.ServiceDTO
import es.dam.bique.microservicioproductoservicios.models.Service
import es.dam.bique.microservicioproductoservicios.models.ServiceType
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.util.*

fun ServiceDTO.toEntity(): Service {
    return Service(
        id = ObjectId(id.toString()).toId(),
        uuid = UUID.fromString(uuid),
        image = image,
        price = price,
        appointment = appointment.toEntity(),
        type = ServiceType.from(type)
    )
}

fun ServiceDTO.toOnSaleDTO (): OnSaleDTO {
    return OnSaleDTO(
        productEntity = null,
        serviceEntity = ServiceDTO(
            id = id.toString().toLong(),
            uuid = uuid,
            image = image,
            price = price,
            appointment = appointment,
            type = type ),
        type = OnSaleDTO.OnSaleType.SERVICE
    )
}


fun Service.toDTO(): ServiceDTO{
    return ServiceDTO(
        id = id.toString().toLong(),
        uuid = uuid.toString(),
        image = image,
        price = price,
        appointment = appointment.toDTO(),
        type = type.value
    )
}

fun ServiceCreateDTO.toModel(): Service {
    return Service(
        image = image,
        price = price,
        appointment = appointment.toEntity(),
        type = ServiceType.from(type)
    )
}