package biques.dam.es.mapper

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderUpdateDTO
import biques.dam.es.models.Order
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.util.*

/**
 * Creates an Order from OrderDTO
 * @return OrderDTO
 * @author BiquesDAM-Team
 */
fun OrderDTO.toEntity(): Order{
    return Order(
        id = ObjectId(this.id).toId(),
        uuid = UUID.fromString(this.uuid),
        status = Order.StatusOrder.from(status),
        total = this.total,
        iva = this.iva,
        orderLine = this.orderLine,
        cliente = this.cliente,
    )
}

/**
 * Creates an OrderDTO from Order
 * @return OrderDTO
 * @author BiquesDAM-Team
 */
fun Order.toDTO(): OrderDTO{
    return OrderDTO(
        id = id.toString(),
        uuid = uuid.toString(),
        status = status.name,
        total = this.total,
        iva = this.iva,
        orderLine = this.orderLine,
        cliente = this.cliente
    )
}

/**
 * Creates an Order from OrderUpdateDTO
 * @return OrderDTO
 * @author BiquesDAM-Team
 */
fun OrderUpdateDTO.toEntity(): Order {
    return Order(
        id = ObjectId(this.id).toId(),
        uuid = UUID.fromString(this.uuid),
        status = Order.StatusOrder.from(status),
        total = this.total,
        iva = this.iva,
        orderLine = this.orderLine,
        cliente = this.cliente
    )
}