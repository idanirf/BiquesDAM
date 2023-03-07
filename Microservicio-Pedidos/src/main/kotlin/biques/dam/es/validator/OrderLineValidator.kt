package biques.dam.es.validator

import biques.dam.es.dto.OrderLineDTO
import io.ktor.server.plugins.requestvalidation.*

/**
 * This class is used to validate the OrderLineDTO
 * @author BiquesDam-Teams
 */
fun RequestValidationConfig.orderLineValidation(){
    validate<OrderLineDTO>{ orderLine ->
        if(orderLine.amount < 0){
            ValidationResult.Invalid("The amount cannot be less than 0")
        } else if (orderLine.price < 0){
            ValidationResult.Invalid("The price cannot be 0 or negative")
        } else if (orderLine.total < 0){
            ValidationResult.Invalid("The total cannot be 0 or negative")
        } else{
            ValidationResult.Valid
        }
    }
}