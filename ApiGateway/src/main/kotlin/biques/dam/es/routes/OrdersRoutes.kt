package biques.dam.es.routes

import biques.dam.es.dto.*
import biques.dam.es.exceptions.OrderBadRequestException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.exceptions.OrderLineBadRequestException
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.repositories.appointment.KtorFitRepositoryAppointment
import biques.dam.es.repositories.orders.KtorFitRepositoryOrders
import biques.dam.es.repositories.ordersLine.KtorFitRepositoryOrdersLine
import biques.dam.es.repositories.sales.KtorFitRepositorySales
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
import java.util.*

private const val ENDPOINT = "order"

fun Application.ordersRoutes() {
    //val orderRepository by inject<KtorFitRepositoryOrders>()
    val orderRepository = KtorFitRepositoryOrders()
    //val orderLineRepository by inject<KtorFitRepositoryOrdersLine>()
    val orderLineRepository = KtorFitRepositoryOrdersLine()
    //val userRepopsitory by inject<KtorFitRepositoryUsers>()
    val userRepopsitory = KtorFitRepositoryUsers()

    routing {
        route("/$ENDPOINT") {
           //authenticate {
                // ORDERS
                get {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val orderDTO = orderRepository.findAll(token.toString()).toList()
                        call.respond(HttpStatusCode.OK, orderDTO)
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                get("/{id}") {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = UUID.fromString(call.parameters["id"])!!
                        val orderDTO = orderRepository.findById(token.toString(), id)
                        val res = mutableListOf<OrderLineDTO>()
                        val user = userRepopsitory.findById(token.toString(), orderDTO.cliente.toLong())
                        orderDTO.orderLine.forEach {
                            res.add(orderLineRepository.findById(token.toString(), UUID.fromString(it)))
                        }
                        val finalOrder = FinalOrderDTO(
                            orderDTO.uuid,
                            orderDTO.status,
                            orderDTO.total,
                            orderDTO.iva,
                            res,
                            user
                        )
                        call.respond(HttpStatusCode.OK, finalOrder)
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                post {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val orderCreate = call.receive<OrderDTOCreate>()
                        val res = mutableListOf<String>()
                        orderCreate.orderLine.forEach {
                            res.add(orderLineRepository.save(token.toString(), it).uuid)
                        }
                        val saveOrder = OrderSaveDTO(
                            orderCreate.status,
                            orderCreate.total,
                            orderCreate.iva,
                            res,
                            orderCreate.cliente
                        )
                        val resOrder = orderRepository.save(token.toString(), saveOrder)
                        call.respond(HttpStatusCode.Created, resOrder)
                    } catch (e: OrderBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                put("/{id}") {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = UUID.fromString(call.parameters["id"])!!
                        val orderUpdate = call.receive<OrderDTOUpdate>()
                        val res = mutableListOf<String>()
                        orderUpdate.orderLine.forEach {
                            res.add(orderLineRepository.findById(token.toString(), UUID.fromString(it)).uuid)
                        }
                        val saveOrder = OrderDTOUpdate(
                            orderUpdate.status,
                            orderUpdate.total,
                            orderUpdate.iva,
                            res,
                            orderUpdate.cliente
                        )
                        val resOrder = orderRepository.update(token.toString(), id, saveOrder)
                        call.respond(HttpStatusCode.Created, resOrder)
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    } catch (e: OrderBadRequestException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                delete("/{id}") {
                    try {
                        val token = call.principal<JWTPrincipal>()
                        val id = UUID.fromString(call.parameters["id"])!!
                        val order = orderRepository.findById(token.toString(), id)
                        order.orderLine.forEach {
                            orderLineRepository.delete(token.toString(), UUID.fromString(it))
                        }
                        orderRepository.delete(token.toString(), UUID.fromString(order.uuid))
                    } catch (e: OrderNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }
            //}
        }
    }

}