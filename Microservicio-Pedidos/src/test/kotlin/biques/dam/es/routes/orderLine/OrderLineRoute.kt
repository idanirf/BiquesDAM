package biques.dam.es.routes.orderLine

import biques.dam.es.models.OrderLine
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
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
        UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
    )

    //FINDALL
    @Test
    fun getAll() = testApplication {
        environment { config }
        val response = client.get("/orderline")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    //POST


    //DELETE


    //UPDATE
}