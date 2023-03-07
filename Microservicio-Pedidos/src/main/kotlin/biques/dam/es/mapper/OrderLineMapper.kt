package biques.dam.es.mapper

import biques.dam.es.dto.OrderLineDTO
import biques.dam.es.models.OrderLine
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.util.*

/**
 * Mapper for OrderLine
 * @return OrderLine
 * @author BiquesDAM-Team
 */
fun OrderLineDTO.toEntity (): OrderLine {
    return OrderLine(
        id = ObjectId(this.id).toId(),
        uuid = UUID.fromString(this.uuid),
        product =  UUID.fromString(this.sale),
        amount = this.amount,
        price = this.price,
        total = this.total,
        employee = this.employee,
    )
}

/**
 * Mapper for OrderLineDTO
 * @return OrderLineDTO
 * @author BiquesDAM-Team
 */
fun OrderLine.toDto (): OrderLineDTO {
    return OrderLineDTO(
        id = this.id.toString(),
        uuid = this.uuid.toString(),
        sale = this.product.toString(),
        amount = this.amount,
        price = this.price,
        total = this.total,
        employee = this.employee,
    )
}

