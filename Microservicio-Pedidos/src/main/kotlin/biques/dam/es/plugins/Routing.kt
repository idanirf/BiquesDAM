package biques.dam.es.plugins

import biques.dam.es.dto.OrderDTO
import biques.dam.es.dto.OrderLineDTO
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
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList


// TODO Mirar catchs
fun Application.configureRouting() {
    val orderService = OrderService(OrderRepositoryImpl())
    val orderLineService = OrderLineService(OrderLineRepositoryImpl())
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/orderline"){
            val res = mutableListOf<OrderLineDTO>()
                orderLineService.getAllOrderLine().collect { orderLine ->
                res.add(orderLine.toDto())
            }
            call.respond(HttpStatusCode.OK, res)
        }
        get("/order"){
           val res = mutableListOf<OrderDTO>()
               orderService.getAllOrder().collect { order ->
               res.add(order.toDTO())
           }
            call.respond(
                HttpStatusCode.OK,res
            )

        }
        get("/order/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val res = orderService.getOrderByUUID(id).toDTO()
                call.respond(HttpStatusCode.OK, res)
            } catch (e: UUIDException) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }

        }
        get("/orderline/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val res = orderLineService.getOrderLineByUUID(id).toDto()
                call.respond(HttpStatusCode.OK, res)
            } catch (e: UUIDException) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

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

        put("/order/{id}"){
            try {
                val id = call.parameters["id"]?.toUUID()!!
                val dto = call.receive<OrderDTO>()
                val order = dto.toEntity()
                val res =  orderService.updateOrder(order)
                call.respond(HttpStatusCode.OK, res.toDTO())
            } catch (e: OrderNotFoundException){
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }

        }
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
        delete("/order/{id}"){
            //TODO Comprobar permisos
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
