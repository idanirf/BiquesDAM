package biques.dam.es.plugins

import biques.dam.es.validator.orderLineValidation
import biques.dam.es.validator.orderValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*



fun Application.configureValidation(){
    install(RequestValidation) {
        orderValidation()
        orderLineValidation()
    }
}
