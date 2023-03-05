package biques.dam.es.routes.order

import biques.dam.es.mapper.toDTO
import biques.dam.es.mapper.toDto
import biques.dam.es.models.Order
import biques.dam.es.models.OrderLine
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.litote.kmongo.id.toId
import java.util.*
import kotlin.test.assertEquals


class OrderRouter {
    private val order = Order(
        ObjectId("223456789912345678901232").toId(),
        UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
        Order.StatusOrder.DELIVERED,
        12.0,
        12.0,
        ObjectId("123456789912345678901232").toId(),
        UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
    )

    private val orderDto = order.toDTO()

    //FINDALL
    @Test
    fun getAll() = testApplication {
        environment { config }
        val response = client.get("/order")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    //POST
    @Test
    fun post() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post("/order") {
            contentType(ContentType.Application.Json)
            setBody(orderDto)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }


    //UPDATE
    @Test
    fun update() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        // SAVE
        val responseSave = client.post("/order") {
            contentType(ContentType.Application.Json)
            setBody(orderDto)
        }
        val order = Order(
            ObjectId("223456789912345678901232").toId(),
            UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
            Order.StatusOrder.DELIVERED,
            23.0,
            12.0,
            ObjectId("123456789912345678901232").toId(),
            UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
        )
        val orderLineDto = order.toDTO()

        val response = client.put("/order/4d83b14d-bdae-422a-9e41-87fbdaef9cff") {
            contentType(ContentType.Application.Json)
            setBody(orderLineDto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }


    //DELETE
    @Test
    fun delete() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        // SAVE
        val responseSave = client.post("/order") {
            contentType(ContentType.Application.Json)
            setBody(orderDto)
        }

        val response = client.delete("/order/4d83b14d-bdae-422a-9e41-87fbdaef9cff") {
            contentType(ContentType.Application.Json)
            setBody(orderDto)
        }
        assertEquals(HttpStatusCode.NoContent, response.status)
    }


}