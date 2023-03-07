package biques.dam.es.routes

import biques.dam.es.dto.UserLoginDTO
import biques.dam.es.dto.UserRegisterDTO
import biques.dam.es.dto.UserUpdateDTO
import biques.dam.es.exceptions.UserBadRequestException
import biques.dam.es.exceptions.UserNotFoundException
import biques.dam.es.repositories.users.KtorFitRepositoryUsers
import biques.dam.es.services.token.TokensService
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger {}
private const val ENDPOINT = "users"

fun Application.usersRoutes() {
    val userRepository by inject<KtorFitRepositoryUsers>(named("KtorFitRepositoryUsers"))
    val tokenService by inject<TokensService>()

    routing {

        route("/$ENDPOINT") {
            post("/login",{

                description = "Login a user"
                securitySchemeName = "JWT-Auth"

                response{
                    HttpStatusCode.Created to {
                        description = "User logged in"
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Bad request"
                    }
                }

            }) {
                logger.debug { "API Gateway -> /login" }

                try {
                    val login = call.receive<UserLoginDTO>()

                    val user = async {
                        userRepository.login(login)
                    }

                    call.respond(HttpStatusCode.OK, user.await())

                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post("/register",{

                    description = "Register a user"
                    securitySchemeName = "JWT-Auth"

                    response{
                        HttpStatusCode.Created to {
                            description = "User registered"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Bad request"
                        }
                    }
            }) {
                logger.debug { "API Gateway -> /register" }

                try {
                    val register = call.receive<UserRegisterDTO>()

                    val user = async {
                        userRepository.register(register)
                    }

                    call.respond(HttpStatusCode.Created, user.await())

                } catch (e: UserBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            authenticate {
                get({
                    description = "Get all users"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "Users found"
                        }
                        HttpStatusCode.NotFound to {
                            description = "Users not found"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Bad request"
                        }
                    }
                }) {
                    logger.debug { "API Gateway -> /users" }
                    try {
                        val token = tokenService.generateToken(call.principal()!!)

                        val res = async {
                            userRepository.findAll("Bearer $token")
                        }
                        val users = res.await()

                        call.respond(HttpStatusCode.OK, users)

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                get("/{id}",{
                    description = "Get a user by id"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "User found"
                        }
                        HttpStatusCode.NotFound to {
                            description = "User not found"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Bad request"
                        }
                    }
                }) {
                    logger.debug { "API Gateway -> /users/id" }

                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]

                        val user = async {
                            userRepository.findById("Bearer $token", id!!.toLong())
                        }

                        call.respond(HttpStatusCode.OK, user.await())

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                put("/{id}",{
                    description = "Update a user by id"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to {
                            description = "User updated"
                        }
                        HttpStatusCode.NotFound to {
                            description = "User not found"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Bad request"
                        }
                    }

                }) {
                    logger.debug { "API Gateway -> /users/id" }

                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]
                        val user = call.receive<UserUpdateDTO>()

                        val updatedUser = async {
                            userRepository.update("Bearer $token", id!!.toLong(), user)
                        }

                        call.respond(HttpStatusCode.OK, updatedUser.await())

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    } catch (e: UserBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                delete("/{id}",{
                    description = "Delete a user by id"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.NoContent to {
                            description = "User deleted"
                        }
                        HttpStatusCode.NotFound to {
                            description = "User not found"
                        }
                        HttpStatusCode.BadRequest to {
                            description = "Bad request"
                        }
                    }
                }) {
                    logger.debug { "API Gateway -> /users/id" }

                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]

                        userRepository.delete("Bearer $token", id!!.toLong())

                        call.respond(HttpStatusCode.NoContent)
                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }
            }
        }
    }
}