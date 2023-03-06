 package biques.dam.es.routes

import biques.dam.es.dto.UserLoginDTO
import biques.dam.es.dto.UserRegisterDTO
import biques.dam.es.dto.UserUpdateDTO
import biques.dam.es.exceptions.UserBadRequestException
import biques.dam.es.exceptions.UserNotFoundException
import biques.dam.es.repositories.users.KtorFitRepositoryUsers
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject
import mu.KotlinLogging
import org.koin.core.qualifier.named

 private val logger = KotlinLogging.logger {}
private const val ENDPOINT = "users"

fun Application.usersRoutes() {
    //val userRepository by inject<KtorFitRepositoryUsers>(named("KtorFitRepositoryUsers"))
    val userRepository = KtorFitRepositoryUsers()

    routing {
        route("/$ENDPOINT") {
            post("/login") {
                logger.debug { "API Gateway -> /login" }

                try {
                    val login = call.receive<UserLoginDTO>()
                    val user = userRepository.login(login)

                    call.respond(HttpStatusCode.OK, user)

                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post("/register") {
                logger.debug { "API Gateway -> /register" }

                try {
                    val register = call.receive<UserRegisterDTO>()
                    val user = userRepository.register(register)

                    call.respond(HttpStatusCode.Created, user)

                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                get {
                    logger.debug { "API Gateway -> /users" }

                    try {
                        val token = call.principal<JWTPrincipal>()
                        val users = userRepository.findAll(token.toString()).toList()

                        call.respond(HttpStatusCode.OK, users)

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                get("/{id}") {
                    logger.debug { "API Gateway -> /users/id" }

                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = call.parameters["id"]
                        val user = userRepository.findById(token.toString(), id!!.toLong())

                        call.respond(HttpStatusCode.OK, user)

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                put("/{id}") {
                    logger.debug { "API Gateway -> /users/id" }

                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = call.parameters["id"]
                        val user = call.receive<UserUpdateDTO>()
                        val updatedUser = userRepository.update(token.toString(), id!!.toLong(), user)

                        call.respond(HttpStatusCode.OK, updatedUser)

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    } catch (e: UserBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                delete("/{id}") {
                    logger.debug { "API Gateway -> /users/id" }

                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = call.parameters["id"]

                        userRepository.delete(token.toString(), id!!.toLong())

                        call.respond(HttpStatusCode.NoContent)
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }
            }
        }
    }
}