package biques.dam.es.routes

import biques.dam.es.dto.*
import biques.dam.es.exceptions.OrderBadRequestException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.repositories.orders.KtorFitRepositoryOrders
import biques.dam.es.repositories.ordersLine.KtorFitRepositoryOrdersLine
import biques.dam.es.repositories.users.KtorFitRepositoryUsers
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

/**
 * Defines the routes for the orders API endpoints.
 * @author BiquedDAM-Team
 */
private const val ENDPOINT = "order"

fun Application.ordersRoutes() {
    val orderRepository by inject<KtorFitRepositoryOrders>(named("KtorFitRepositoryOrders"))
    val orderLineRepository by inject<KtorFitRepositoryOrdersLine>(named("KtorFitRepositoryOrdersLine"))
    val userRepopsitory by inject<KtorFitRepositoryUsers>(named("KtorFitRepositoryUsers"))
    val tokenService by inject<TokensService>()

    routing {
        route("/$ENDPOINT") {
            authenticate {

                /**
                 * Retrieves all orders.
                 * @throws OrderNotFoundException if the order is not found.
                 */
                get {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)
                        //if(originalToken.payload.getClaim("rol").split(",").toSet().toString())

                        val orderDTO = async {
                            orderRepository.findAll("Bearer $token").toList()
                        }

                        call.respond(HttpStatusCode.OK, orderDTO.await())
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                /**
                 * Retrieves an order by id.
                 * @param id the id of the order to retrieve.
                 * @throws OrderNotFoundException if the order is not found.
                 */
                get("/{id}") {
                    try {
                        val token = tokenService.generateToken(call.principal()!!)
                        val id = UUID.fromString(call.parameters["id"])!!

                        val orderDTO = async {
                            orderRepository.findById("Bearer $token", id)
                        }.await()

                        val res = mutableListOf<OrderLineDTO>()


                        orderDTO.orderLine.forEach {
                            res.add(
                                async {
                                    orderLineRepository.findById("Bearer $token", UUID.fromString(it))
                                }.await()
                            )
                        }
                        val cliente = userRepopsitory.findById("Bearer $token", orderDTO.cliente.toLong())

                        val finalOrder = FinalOrderDTO(
                            orderDTO.uuid,
                            orderDTO.status,
                            orderDTO.total,
                            orderDTO.iva,
                            res,
                            cliente
                        )
                        call.respond(HttpStatusCode.OK, finalOrder)
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                /**
                 * Creates a new order.
                 * @throws OrderBadRequestException if there is a problem with the request body.
                 */
                post {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("[ADMIN]") ||
                            originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")
                        ) {
                            val orderCreate = call.receive<OrderDTOCreate>()
                            val res = mutableListOf<String>()

                            orderCreate.orderLine.forEach {
                                res.add(
                                    async {
                                        orderLineRepository.save("Bearer $token", it).uuid
                                    }.await()!!
                                )
                            }

                            val saveOrder = OrderSaveDTO(
                                orderCreate.status,
                                orderCreate.total,
                                orderCreate.iva,
                                res,
                                orderCreate.cliente
                            )

                            val resOrder = async {
                                orderRepository.save("Bearer $token", saveOrder)
                            }.await()

                            call.respond(HttpStatusCode.Created, resOrder)

                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: OrderBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                /**
                 * Updates an existing order by ID.
                 * @throws OrderNotFoundException if the specified order ID cannot be found.
                 * @throws OrderBadRequestException if there is a problem with the request body.
                 */
                put("/{id}") {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("[ADMIN]") ||
                            originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")
                        ) {
                            val id = UUID.fromString(call.parameters["id"])!!
                            val orderUpdate = call.receive<OrderDTOUpdate>()
                            val res = mutableListOf<String>()

                            orderUpdate.orderLine.forEach {
                                res.add(
                                    async {
                                        orderLineRepository.findById("Bearer $token", UUID.fromString(it)).uuid
                                    }.await()!!
                                )
                            }

                            val saveOrder = OrderDTOUpdate(
                                orderUpdate.status,
                                orderUpdate.total,
                                orderUpdate.iva,
                                res,
                                orderUpdate.cliente
                            )

                            val resOrder = async {
                                orderRepository.update("Bearer $token", id, saveOrder)
                            }.await()

                            call.respond(HttpStatusCode.OK, resOrder)
                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }

                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    } catch (e: OrderBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                /**
                 * Deletes an existing order by ID.
                 * @throws OrderNotFoundException if the specified order ID cannot be found.
                 */
                delete("/{id}") {
                    try {
                        val originalToken = call.principal<JWTPrincipal>()!!
                        val token = tokenService.generateToken(originalToken)

                        if (originalToken.payload.getClaim("rol").toString().contains("SUPERADMIN")) {
                            val id = UUID.fromString(call.parameters["id"])!!

                            val order = async {
                                orderRepository.findById("Bearer $token", id)
                            }.await()

                            order.orderLine.forEach {
                                async {
                                    orderLineRepository.delete("Bearer $token", UUID.fromString(it))
                                }.await()
                            }

                            async {
                                orderRepository.delete("Bearer $token", UUID.fromString(order.uuid))
                            }.await()

                            call.respond(HttpStatusCode.NoContent)

                        } else {
                            call.respond(HttpStatusCode.Unauthorized, "You are not authorized")
                        }
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }
            }
        }
    }

}