package biques.dam.es.mapper

import biques.dam.es.dto.OrderLineDTO
import biques.dam.es.models.OrderLine
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.util.*


fun OrderLineDTO.toEntity (): OrderLine {
    return OrderLine(
        id = ObjectId(this.id).toId(),
        uuid = UUID.fromString(this.uuid),
        //product = ObjectId(this.product).toId(),
        amount = this.amount,
        price = this.price,
        total = this.total,
        // employee = ObjectId(this.employee).toId()
    )
}

fun OrderLine.toDto (): OrderLineDTO {
    return OrderLineDTO(
        id = this.id.toString(),
        uuid = this.uuid.toString(),
        // product = this.product.toString(),
        amount = this.amount,
        price = this.price,
        total = this.total,
        // employee = this.employee.toString(),
    )
}

