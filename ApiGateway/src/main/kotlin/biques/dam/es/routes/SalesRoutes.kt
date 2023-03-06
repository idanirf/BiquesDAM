package biques.dam.es.routes

import biques.dam.es.dto.FinalSaleDTO
import biques.dam.es.dto.FinalServiceDTO
import biques.dam.es.dto.SaleCreateDTO
import biques.dam.es.exceptions.SaleNotFoundException
import biques.dam.es.repositories.appointment.KtorFitRepositoryAppointment
import biques.dam.es.repositories.sales.KtorFitRepositorySales
import biques.dam.es.services.token.TokensService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject
import java.util.*

private const val ENDPOINT = "sales"

fun Application.salesRoutes() {
    val salesRepository by inject<KtorFitRepositorySales>(named("KtorFitRepositorySales"))
    val appointmentRepository by inject<KtorFitRepositoryAppointment>(named("KtorFitRepositoryAppointment"))
    val tokenService by inject<TokensService>()

    routing {
        route("/$ENDPOINT") {
            authenticate {
                get {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        val list = async {
                            salesRepository.findAll("Bearer $token").toList()
                        }

                        val result: MutableList<FinalSaleDTO> = mutableListOf()
                        list.await().forEach {
                            if (it.type == "SERVICE") {
                                val service = FinalSaleDTO(
                                    productEntity = null,
                                    serviceEntity = FinalServiceDTO(
                                        id = it.serviceEntity!!.id,
                                        uuid = it.serviceEntity.uuid,
                                        image = it.serviceEntity.image,
                                        price = it.serviceEntity.price,
                                        appointment = async {
                                            appointmentRepository.findById(
                                                "Bearer $token",
                                                UUID.fromString(it.serviceEntity.appointment)
                                            )
                                        }.await(),
                                        type = it.serviceEntity.type
                                    ),
                                    type = "SERVICE"
                                )

                                result.add(service)
                            } else {
                                val product = FinalSaleDTO(
                                    productEntity = it.productEntity,
                                    serviceEntity = null,
                                    type = "PRODUCT"
                                )
                                result.add(product)
                            }
                        }

                        call.respond(HttpStatusCode.OK, result)

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }

                }

                get("/product/{id}") {
                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]!!

                        val list = async {
                            salesRepository.findById("Bearer $token", id)
                        }

                        list.await().forEach {
                            if (it.type == "PRODUCT") {
                                call.respond(HttpStatusCode.OK, it.productEntity!!)
                            }
                        }

                        call.respond(HttpStatusCode.NotFound, "Product not found with uuid: $id")

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                get("/service/{id}") {
                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]!!

                        val list = async {
                            salesRepository.findById("Bearer $token", id)
                        }

                        list.await().forEach {
                            if (it.type == "SERVICE") {
                                val result = FinalServiceDTO(
                                    id = it.serviceEntity!!.id,
                                    uuid = it.serviceEntity.uuid,
                                    image = it.serviceEntity.image,
                                    price = it.serviceEntity.price,
                                    appointment = async {
                                        appointmentRepository.findById(
                                            "Bearer $token",
                                            UUID.fromString(it.serviceEntity.appointment)
                                        )
                                    }.await(),
                                    type = it.serviceEntity.type
                                )

                                call.respond(HttpStatusCode.OK, result)
                            }
                        }

                        call.respond(HttpStatusCode.NotFound, "Service not found with uuid: $id")

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                post {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("[ADMIN]") ||
                            originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")
                        ) {

                            val dto = call.receive<SaleCreateDTO>()

                            val result = async {
                                salesRepository.save("Bearer $token", dto)
                            }

                            val res = result.await()

                            if (res.type == "SERVICE") {
                                val res = FinalServiceDTO(
                                    id = res.serviceEntity!!.id,
                                    uuid = res.serviceEntity.uuid,
                                    image = res.serviceEntity.image,
                                    price = res.serviceEntity.price,
                                    appointment = async {
                                        appointmentRepository.findById(
                                            "Bearer $token",
                                            UUID.fromString(res.serviceEntity.appointment)
                                        )
                                    }.await(),
                                    type = res.serviceEntity.type
                                )

                                call.respond(HttpStatusCode.Created, res)

                            } else {
                                call.respond(HttpStatusCode.Created, res)
                            }
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }

                }


                put("/{id}") {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("[ADMIN]") ||
                            originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")
                        ) {

                            val id = call.parameters["id"]
                            val dto = call.receive<SaleCreateDTO>()

                            val result = async {
                                salesRepository.update("Bearer $token", UUID.fromString(id), dto)
                            }

                            val res = result.await()

                            if (res.type == "SERVICE") {
                                val res = FinalServiceDTO(
                                    id = res.serviceEntity!!.id,
                                    uuid = res.serviceEntity.uuid,
                                    image = res.serviceEntity.image,
                                    price = res.serviceEntity.price,
                                    appointment = async {
                                        appointmentRepository.findById(
                                            "Bearer $token",
                                            UUID.fromString(res.serviceEntity.appointment)
                                        )
                                    }.await(),
                                    type = res.serviceEntity.type
                                )
                                call.respond(HttpStatusCode.OK, res)

                            } else {
                                call.respond(HttpStatusCode.OK, res)
                            }
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }

                }

                delete("/{id}") {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("[ADMIN]")) {
                            val id = call.parameters["id"]

                            val result = async {
                                salesRepository.delete("Bearer $token", UUID.fromString(id))
                            }

                            call.respond(HttpStatusCode.NoContent)
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }

                }
            }
        }
    }
}