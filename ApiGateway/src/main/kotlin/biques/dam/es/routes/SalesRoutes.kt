package biques.dam.es.routes

import biques.dam.es.dto.FinalSaleDTO
import biques.dam.es.dto.FinalServiceDTO
import biques.dam.es.dto.SaleDTO
import biques.dam.es.exceptions.SaleNotFoundException
import biques.dam.es.repositories.appointment.KtorFitRepositoryAppointment
import biques.dam.es.repositories.sales.KtorFitRepositorySales
import biques.dam.es.services.token.TokensService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
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
                        val token = tokenService.generateToken(call.principal()!!)
                        val list = salesRepository.findAll(token.toString())
                            .toList()

                        val result : MutableList<FinalSaleDTO> = mutableListOf()

                        list.forEach {
                            if (it.type == "SERVICE") {
                                val service = FinalSaleDTO (
                                    productEntity = null,
                                    serviceEntity = FinalServiceDTO(
                                        id = it.serviceEntity!!.id,
                                        uuid = it.serviceEntity.uuid,
                                        image = it.serviceEntity.image,
                                        price = it.serviceEntity.price,
                                        appointment = appointmentRepository.findById(
                                            token.toString(),
                                            UUID.fromString(it.serviceEntity.appointment)
                                        ),
                                        type = it.serviceEntity.type
                                    ),
                                    type = "SERVICE"
                                )

                                result.add(service)
                            } else {
                                val product = FinalSaleDTO (
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
                        val id = call.parameters["id"]
                        val list = salesRepository.findById(token.toString(), UUID.fromString(id)).toList()

                        list.forEach {
                            if (it.type== "PRODUCT") {
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
                        val id = call.parameters["id"]
                        val list = salesRepository.findById(token.toString(), UUID.fromString(id)).toList()

                        list.forEach {
                            if (it.type == "SERVICE") {
                                val result = FinalServiceDTO(
                                    id = it.serviceEntity!!.id,
                                    uuid = it.serviceEntity.uuid,
                                    image = it.serviceEntity.image,
                                    price = it.serviceEntity.price,
                                    appointment = appointmentRepository.findById(token.toString(), UUID.fromString(it.serviceEntity.appointment)),
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


                put("/{id}") {
                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]
                        val dto = call.receive<SaleDTO>()
                        val result = salesRepository.update(token.toString(), UUID.fromString(id), dto)

                        if (result.type == "SERVICE") {
                            val result = FinalServiceDTO(
                                id = result.serviceEntity!!.id,
                                uuid = result.serviceEntity.uuid,
                                image = result.serviceEntity.image,
                                price = result.serviceEntity.price,
                                appointment = appointmentRepository.findById(token.toString(), UUID.fromString(result.serviceEntity.appointment)),
                                type = result.serviceEntity.type
                            )
                            call.respond(HttpStatusCode.OK, result)

                        }else{
                            call.respond(HttpStatusCode.OK, result)
                        }

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }

                }

                delete("/{id}") {
                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = call.parameters["id"]
                        val result = salesRepository.delete(token.toString(), UUID.fromString(id))

                        call.respond(HttpStatusCode.NoContent, result)

                    } catch (e: SaleNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }

                }
            }
        }
    }
}