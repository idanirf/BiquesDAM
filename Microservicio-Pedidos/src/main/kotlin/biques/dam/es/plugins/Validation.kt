package biques.dam.es.plugins

import biques.dam.es.validator.orderLineValidation
import biques.dam.es.validator.orderValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

/**
 * Configures request validation with the provided validators.
 * @author BiquesDAM-Team
 */
fun Application.configureValidation(){
    install(RequestValidation) {
        orderValidation()
        orderLineValidation()
    }
}
