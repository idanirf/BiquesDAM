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
        listOf(
            "ed6f7d0a-7f7a-45bf-8b63-a1aa21383271",
            "e213f434-4c2b-4a28-953f-3981b1ff7e00",
        ),
        1
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
            listOf(
                "ed6f7d0a-7f7a-45bf-8b63-a1aa21383271",
                "e213f434-4c2b-4a28-953f-3981b1ff7e00",
            ),
            1
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