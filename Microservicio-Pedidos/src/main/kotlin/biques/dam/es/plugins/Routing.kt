package biques.dam.es.plugins

import biques.dam.es.dto.OrderAllDTO
import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderLineDTO
import biques.dam.es.dto.OrderUpdateDTO
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.exceptions.UUIDException
import biques.dam.es.mapper.toDTO
import biques.dam.es.mapper.toDto
import biques.dam.es.mapper.toEntity
import biques.dam.es.repositories.order.OrderRepository
import biques.dam.es.repositories.order.OrderRepositoryImpl
import biques.dam.es.repositories.orderline.OrderLineRepositoryImpl
import biques.dam.es.services.OrderLineService
import biques.dam.es.services.OrderService
import biques.dam.es.utils.toUUID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList


/**
 * Configures the routing of the application.
 * @param orderService the service to use for the order service.
 * @param orderLineService the service to use for the order line service.
 * @author BiquesDam-Team
 */
fun Application.configureRouting() {
    val orderService = OrderService(OrderRepositoryImpl())
    val orderLineService = OrderLineService(OrderLineRepositoryImpl())
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        /**
         * Returns all order lines.
         * @return a list of OrderLineDTO objects representing all order lines in the system.
         */
        get("/orderline"){
            val res = mutableListOf<OrderLineDTO>()
                orderLineService.getAllOrderLine().collect { orderLine ->
                res.add(orderLine.toDto())
            }
            call.respond(HttpStatusCode.OK, res)
        }

        /**
         * Returns all orders.
         * @return an OrderAllDTO object representing all orders in the system.
         */
        get("/order"){
           val res = mutableListOf<OrderDTO>()
               orderService.getAllOrder().collect { order ->
               res.add(order.toDTO())
           }

            val orders = OrderAllDTO(
                res
            )
            call.respond(
                HttpStatusCode.OK,orders
            )

        }

        /**
         * Returns the order with the specified id.
         * @param id a string representing the UUID of the order to retrieve.
         * @return an OrderDTO object representing the order with the specified id.
         * @throws UUIDException if the provided id is not a valid UUID.
         */
        get("/order/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val res = orderService.getOrderByUUID(id).toDTO()
                call.respond(HttpStatusCode.OK, res)
            } catch (e: UUIDException) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

        /**
         * Returns the order line with the specified id.
         * @param id a string representing the UUID of the order line to retrieve.
         * @return an OrderLineDTO object representing the order line with the specified id.
         * @throws UUIDException if the provided id is not a valid UUID.
         */
        get("/orderline/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val res = orderLineService.getOrderLineByUUID(id).toDto()
                call.respond(HttpStatusCode.OK, res)
            } catch (e: UUIDException) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

        /**
         * Creates a new order.
         * @param dto an OrderDTO object representing the order to create.
         * @return an OrderDTO object representing the newly created order.
         * @throws OrderNotFoundException if the order specified in the OrderDTO does not exist.
         * @throws RequestValidationException if the provided request body is not valid.
         */
        post("/order"){
            try {
                val dto = call.receive<OrderDTO>()
                val order = dto.toEntity()
                val res = orderService.saveOrder(order)
                call.respond(HttpStatusCode.Created, res.toDTO())
            }catch (e: OrderNotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            }catch (e: RequestValidationException) {
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }

        }

        /**
         * Creates a new order line.
         * @param dto an OrderLineDTO object representing the order line to create.
         * @return an OrderLineDTO object representing the newly created order line.
         * @throws OrderLineNotFoundException if the order line specified in the OrderLineDTO does not exist.
         * @throws RequestValidationException if the provided request body is not valid.
         */
        post("/orderline"){
            try {
                val dto = call.receive<OrderLineDTO>()
                val orderLine = dto.toEntity()
                val res = orderLineService.saveOrderLine(orderLine)
                call.respond(HttpStatusCode.Created, res.toDto())
            }catch (e: OrderLineNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())

            } catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }

        /**
         * Updates the order with the specified id.
         * @param id a string representing the UUID of the order to update.
         * @param dto an OrderDTO object representing the updated order information.
         * @return an OrderDTO object representing the updated order.
         * @throws OrderNotFoundException if the order with the specified id does not exist.
         * @throws RequestValidationException if the provided request body is not valid.
         */
        put("/order/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val dto = call.receive<OrderDTO>()
                val oldOrder = orderService.getAllOrder().toList().firstOrNull { it.uuid == id }
                val orderUpdateDto = OrderUpdateDTO(
                    oldOrder!!.id.toString(),
                    oldOrder.uuid.toString(),
                    dto.status,
                    dto.total,
                    dto.iva,
                    dto.orderLine,
                    dto.cliente
                )
                val orderUpdate = orderUpdateDto.toEntity()
                val res =  orderService.updateOrder(orderUpdate)
                call.respond(HttpStatusCode.OK, res.toDTO())
            } catch (e: OrderNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }

        }

        /**
         * Updates the order line with the specified id.
         * @param id a string representing the UUID of the order line to update.
         * @param dto an OrderLineDTO object representing the updated order line information.
         * @return an OrderLineDTO object representing the updated order line.
         * @throws OrderLineNotFoundException if the order line with the specified id does not exist.
         * @throws RequestValidationException if the provided request body is not valid.
         */
        put("/orderline/{id}"){
            try{
                val id = call.parameters["id"]?.toUUID()!!
                val dto = call.receive<OrderLineDTO>()
                val orderLine = dto.toEntity()
                val res = orderLineService.updateOrderLine(orderLine)
                call.respond(HttpStatusCode.OK, res.toDto())
            }catch (e: OrderLineNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }

        /**
         * Deletes the order with the specified id.
         * @param id a string representing the UUID of the order to delete.
         * @throws OrderNotFoundException if the order with the specified id does not exist.
         * @throws RequestValidationException if the provided request body is not valid.
         */
        delete("/order/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val order = orderService.getOrderByUUID(id)
                val res =  orderService.deleteOrder(order)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: OrderNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }

        /**
         * Deletes the order line with the specified id.
         * @param id a string representing the UUID of the order line to delete.
         * @throws OrderLineNotFoundException if the order line with the specified id does not exist.
         * @throws RequestValidationException if the provided request body is not valid.
         */
        delete("/orderline/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val orderLine = orderLineService.getOrderLineByUUID(id)
                val res =  orderLineService.deleteOrderLine(orderLine)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: OrderLineNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }
    }
}
