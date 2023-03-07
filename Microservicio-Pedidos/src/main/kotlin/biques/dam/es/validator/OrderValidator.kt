package biques.dam.es.validator

import biques.dam.es.dto.OrderDTO
import io.ktor.server.plugins.requestvalidation.*

/**
 * This class is used to validate the OrderDTO
 * @author BiquesDam-Teams
 */
fun RequestValidationConfig.orderValidation(){
    validate<OrderDTO>{order ->
        if(order.orderLine.isEmpty()){
            ValidationResult.Invalid("The order line caanot be empty")
        } else if (order.iva < 0){
            ValidationResult.Invalid("The IVA cannot be negative")
        } else if (order.status.isEmpty()){
            ValidationResult.Invalid("The status cannot be empty")
        } else if (order.total < 0){
            ValidationResult.Invalid("The total cannot be negative")
        }else {
            ValidationResult.Valid
        }
    }
}


