package biques.dam.es.plugins

import biques.dam.es.repositories.users.KtorFitRepositoryUsers
import biques.dam.es.routes.ordersRoutes
import biques.dam.es.routes.salesRoutes
import biques.dam.es.routes.usersRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Plugin to configure the routing of the HTTP server
 * @author The BiquesDAM Team
 */
fun Application.configureRouting() {
    val userRepository = KtorFitRepositoryUsers()
    routing {
        get("/") {
            call.respondText("BIQUESDAM: PROYECTO FINAL AD-PSP")
        }

    }

    usersRoutes()
    salesRoutes()
    ordersRoutes()
}
