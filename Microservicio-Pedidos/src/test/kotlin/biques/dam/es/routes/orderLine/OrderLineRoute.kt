package biques.dam.es.routes.orderLine

import biques.dam.es.mapper.toDTO
import biques.dam.es.mapper.toDto
import biques.dam.es.models.OrderLine
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.litote.kmongo.id.toId
import org.litote.kmongo.json
import java.util.*
import kotlin.test.assertEquals

class OrderLineRoute {
    private val orderLine = OrderLine(
        ObjectId("123456789912345678901232").toId(),
        UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
        UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
        3,
        5.0,
        15.0,
        1
    )
    private val orderLineDto = orderLine.toDto()


    @Test
    fun getAll() = testApplication {
        environment { config }
        val response = client.get("/orderline")
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
        val response = client.post("/orderline") {
            contentType(ContentType.Application.Json)
            setBody(orderLineDto)
        }
        assertEquals(HttpStatusCode.Created, response.status)
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
        val responseSave = client.post("/orderline") {
            contentType(ContentType.Application.Json)
            setBody(orderLineDto)
        }

        val response = client.delete("/orderline/4d83b14d-bdae-422a-9e41-87fbdaef9cff") {
            contentType(ContentType.Application.Json)
            setBody(orderLineDto)
        }
        assertEquals(HttpStatusCode.NoContent, response.status)
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
        val responseSave = client.post("/orderline") {
            contentType(ContentType.Application.Json)
            setBody(orderLineDto)
        }
        val orderLine = OrderLine(
            ObjectId("123456789912345678901232").toId(),
            UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
            UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
            2,
            5.0,
            15.0,
            1
        )
        val orderLineDto = orderLine.toDto()

        val response = client.put("/orderline/4d83b14d-bdae-422a-9e41-87fbdaef9cff") {
            contentType(ContentType.Application.Json)
            setBody(orderLineDto)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }
}