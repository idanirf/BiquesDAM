package biques.dam.es.plugins

import biques.dam.es.routes.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("BIQUESDAM: PROYECTO FINAL AD-PSP")
        }
    }

    usersRoutes()
    salesRoutes()
    ordersRoutes()
}
