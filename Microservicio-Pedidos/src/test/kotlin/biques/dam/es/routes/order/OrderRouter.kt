package biques.dam.es.routes.order

import biques.dam.es.mapper.toDTO
import biques.dam.es.models.Order
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
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
        val response = client.post("/api/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(orderDto)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }


    //UPDATE


    //DELETE


}