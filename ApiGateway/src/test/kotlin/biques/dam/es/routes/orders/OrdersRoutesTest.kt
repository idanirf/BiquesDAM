package biques.dam.es.routes.orders

import biques.dam.es.dto.*
import biques.dam.es.routes.users.json
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.*
import java.util.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class OrdersRoutesTest {

    private val loginDTO = UserLoginDTO(
        username = "super",
        password = "super1234"
    )

    private val orderCreate = OrderDTOCreate(
        "FINISHED",
        12.0,
        1.0,
        listOf(
            OrderLineCreateDTO(
                "f00265e8-fbba-408b-9976-973a51f96202",
                1,
                10.0,
                10.0,
                1
            )
        ),
        1
    )

    private val createProduct = SaleCreateDTO(
        productEntity = ProductCreateDTO(
            image = "create",
            brand = "create",
            model = "create",
            description = "create",
            price = 0.0f,
            discountPercentage = 0.0f,
            stock = "LIMITED",
            isAvailable = true,
            type = "BIKES"
        ),
        serviceEntity = null,
        type = "PRODUCT"
    )

    @Test
    @Order(1)
    fun testGetAll() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        val response = client.get("/order") {
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(2)
    fun testGetOrderById() = testApplication {

        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        val res = client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val uuidSale = json.decodeFromString<SaleDTO>(res.bodyAsText()).productEntity!!.uuid

        val orderCreate2 = OrderDTOCreate(
            "FINISHED",
            12.0,
            1.0,
            listOf(
                OrderLineCreateDTO(
                    uuidSale,
                    1,
                    10.0,
                    10.0,
                    1
                )
            ),
            1
        )


        val post = client.post("/order") {
            contentType(ContentType.Application.Json)
            setBody(orderCreate2)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val uuid = json.decodeFromString<OrderDTO>(post.bodyAsText()).uuid

        val response = client.get("/order/$uuid") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val dto = json.decodeFromString<FinalOrderDTO>(response.bodyAsText())

        assertAll(
            { assertEquals(HttpStatusCode.OK, response.status) },
        )
    }

    @Test
    @Order(3)
    fun update() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        assertEquals(login.status, HttpStatusCode.OK)

        val result = login.bodyAsText()
        val dto = json.decodeFromString<UserTokenDTO>(result)

        val post = client.post("/order") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
            setBody(orderCreate)
        }

        val uuid = json.decodeFromString<OrderDTO>(post.bodyAsText()).uuid
        val uuidOrderLine = json.decodeFromString<OrderDTO>(post.bodyAsText()).orderLine.first()

        val updateDTO = OrderDTOUpdate(
            "FINISHED",
            11.0,
            1.0,
            listOf(
                uuidOrderLine
            ),
            1
        )

        val response = client.put("/order/$uuid") {
            contentType(ContentType.Application.Json)
            setBody(updateDTO)
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(6)
    fun delete() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        assertEquals(login.status, HttpStatusCode.OK)

        val result = login.bodyAsText()
        val dto = json.decodeFromString<UserTokenDTO>(result)

        val post = client.post("/order") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
            setBody(orderCreate)
        }

        val uuid = json.decodeFromString<OrderDTO>(post.bodyAsText()).uuid


        val response = client.delete("/order/$uuid") {
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }

        assertEquals(HttpStatusCode.NoContent, response.status)
    }

}
 
