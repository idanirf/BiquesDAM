package biques.dam.es.mapper

import biques.dam.es.dto.OrderDTO
import biques.dam.es.models.Order
import biques.dam.es.models.OrderLine
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.util.*


fun OrderDTO.toEntity(): Order{
    return Order(
        id = ObjectId(this.id).toId(),
        uuid = UUID.fromString(this.uuid),
        status = Order.StatusOrder.from(status),
        total = this.total,
        IVA = this.IVA,
        OrderLine = this.OrderLine.toEntity()
    )
}

fun Order.toDTO(): OrderDTO{
    return OrderDTO(
        id = id.toString(),
        uuid = uuid.toString(),
        status = status.name,
        total = this.total,
        IVA = this.IVA,
        OrderLine = this.OrderLine.toDto()
    )
}