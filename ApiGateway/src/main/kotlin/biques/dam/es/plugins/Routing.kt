package biques.dam.es.plugins

import biques.dam.es.dto.UserLoginDTO
import biques.dam.es.exceptions.UserBadRequestException
import biques.dam.es.repositories.users.KtorFitRepositoryUsers
import biques.dam.es.routes.*
import biques.dam.es.services.users.KtorFitClientUsers
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

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
