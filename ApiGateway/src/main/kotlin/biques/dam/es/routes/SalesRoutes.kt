package biques.dam.es.routes

import biques.dam.es.dto.AppointmentCreateDTO
import biques.dam.es.dto.FinalSaleDTO
import biques.dam.es.dto.FinalServiceDTO
import biques.dam.es.dto.SaleCreateDTO
import biques.dam.es.exceptions.OrderBadRequestException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.exceptions.SaleNotFoundException
import biques.dam.es.repositories.appointment.KtorFitRepositoryAppointment
import biques.dam.es.repositories.sales.KtorFitRepositorySales
import biques.dam.es.services.token.TokensService
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
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

/**
 * Represents the sales routes of the application.
 * @author BiquesDAM-Team
 */
private const val ENDPOINT = "sales"

fun Application.salesRoutes() {
    val salesRepository by inject<KtorFitRepositorySales>(named("KtorFitRepositorySales"))
    val appointmentRepository by inject<KtorFitRepositoryAppointment>(named("KtorFitRepositoryAppointment"))
    val tokenService by inject<TokensService>()

    routing {
        route("/$ENDPOINT") {
            authenticate {
                /**
                 * Retrieves all sales.
                 * @throws SaleNotFoundException if the sale is not found.
                 */

                get({
                    description = "Get all sales"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to{
                            description = "Sales found"
                        }
                        HttpStatusCode.NotFound to{
                            description = "Sales not found"
                        }
                        HttpStatusCode.BadRequest to{
                            description = "Bad request"
                        }
                    }

                }) {
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

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Sales not found")
                    }

                }

                /**
                 * Retrieves a sale by id.
                 * @param id a string representing the id of the sale.
                 * @throws SaleNotFoundException if the sale is not found.
                 */
                get("/product/{id}",{
                    description = "Get a product by id"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to{
                            description = "Product found"
                        }
                        HttpStatusCode.NotFound to{
                            description = "Product not found"
                        }
                        HttpStatusCode.BadRequest to{
                            description = "Bad request"
                        }
                    }

                }) {
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

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Sale with that id was not found")
                    }
                }

                /**
                 * Retrieves a service by id.
                 * @param id a string representing the id of the service.
                 * @throws SaleNotFoundException if the service is not found.
                 */
                get("/service/{id}",{

                    description = "Get a service by id"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.OK to{
                            description = "Service found"
                        }
                        HttpStatusCode.NotFound to{
                            description = "Service not found"
                        }
                        HttpStatusCode.BadRequest to{
                            description = "Bad request"
                        }
                    }
                }) {
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

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Sale with that id was not found")
                    }
                }

                /**
                 * Creates a sale.
                 * @param dto a SaleCreateDTO representing the sale to be created.
                 * @throws SaleNotFoundException if the sale is not found.
                 */
                post({
                    description = "Create a sale"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.Created to{
                            description = "Sale created"
                        }
                        HttpStatusCode.BadRequest to{
                            description = "Bad request"
                        }
                    }
                }) {
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

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Error creating sale")
                    }

                }

                post("/appointments") {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)


                        if (originalToken.payload.getClaim("rol").toString().contains("[ADMIN]") ||
                            originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")
                        ) {

                            val dto = call.receive<AppointmentCreateDTO>()

                            val result = async {
                                appointmentRepository.save("Bearer $token", dto)
                            }

                            val res = result.await()

                            call.respond(HttpStatusCode.Created, res)
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Error creating appointment")
                    }
                }


                /**
                 * Updates an existing sale by ID.
                 * @throws SaleNotFoundException if the specified order ID cannot be found.
                 * @throws SaleBadRequestException if there is a problem with the request body.
                 */
                put("/{id}",{
                    description = "Update a sale"
                    securitySchemeName = "JWT-Auth"
                    response {
                        HttpStatusCode.OK to{
                            description = "Sale updated"
                        }
                        HttpStatusCode.BadRequest to{
                            description = "Bad request"
                        }
                    }
                }) {
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

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Error updating sale")
                    }

                }

                /**
                 * Deletes an existing order by ID.
                 * @throws OrderNotFoundException if the specified order ID cannot be found.
                 */
                delete("/{id}",{

                    description = "Delete a sale"
                    securitySchemeName = "JWT-Auth"

                    response {
                        HttpStatusCode.NoContent to{
                            description = "Sale deleted"
                        }
                        HttpStatusCode.BadRequest to{
                            description = "Bad request"
                        }
                        HttpStatusCode.NotFound to{
                            description = "Sale not found"
                        }
                    }
                }) {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")) {
                            val id = call.parameters["id"]

                            val result = async {
                                salesRepository.delete("Bearer $token", UUID.fromString(id))
                            }

                            call.respond(HttpStatusCode.NoContent)
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.NotFound, "Sale with that id was not found")
                    }

                }
            }
        }
    }
}