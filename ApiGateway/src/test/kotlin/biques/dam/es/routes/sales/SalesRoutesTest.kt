package biques.dam.es.routes.sales

import biques.dam.es.dto.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SalesRoutesTest {

    val createProduct = SaleCreateDTO(
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

    val appointment = AppointmentDTO(
        id = 1L,
        uuid = "e8c8a17a-25e7-41c5-9de9-265923c14483",
        user = "e8c8a17a-25e7-41c5-9de9-265923c14482",
        assistance = "ANY",
        date = "2023-03-03",
        description = "test"
    )

    val createService = SaleCreateDTO(
        productEntity = null,
        serviceEntity = ServiceCreateDTO(
            image = "create",
            price = 0.0f,
            appointment = appointment.uuid,
            type = "REVISION"
        ),
        type = "SERVICE"
    )

    val createAppointment = AppointmentCreateDTO(
        userId = "e8c8a17a-25e7-41c5-9de9-265923c14482",
        assistance = "ANY",
        date = "2023-03-03",
        description = "test"
    )


    @Test
    @Order(1)
    fun testGetAll() = testApplication {
        environment { config }

        val response = client.get("/sales")

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(2)
    fun testPostService() = testApplication {

        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
        }

        val response = client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createService)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        val result = response.bodyAsText()

        val dto = json.decodeFromString<FinalSaleDTO>(result)
        assertAll(
            { assertEquals(createService.serviceEntity?.image, dto.image) },
            { assertEquals(createService.serviceEntity?.type, dto.type) }
        )
    }

    @Test
    @Order(3)
    fun testPostProduct() = testApplication {

        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        val result = response.bodyAsText()

        val dto = json.decodeFromString<SaleDTO>(result)
        assertAll(
            { assertEquals(createService.productEntity?.image, dto.image) },
            { assertEquals(createService.productEntity?.brand, dto.type) }
        )
    }

    @Test
    @Order(4)
    fun testPutProduct() = testApplication {

        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        val response = client.put("/sales/1") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        assertEquals(HttpStatusCode.OK, response.status)

    }

    @Test
    @Order(5)
    fun testPutServices() = testApplication {

        environment { config }

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        val response = client.put("/sales/1") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        assertEquals(HttpStatusCode.OK, response.status)

    }

    @Test
    @Order(6)
    fun testDelete() = testApplication {

        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        var response = client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        response = client.delete("/sales/1")


        assertEquals(HttpStatusCode.NoContent, response.status)
    }

    @Test
    @Order(7)
    fun testGetServiceById() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        var response = client.get("/sales/service/1") {
            contentType(ContentType.Application.Json)
        }


        assertEquals(HttpStatusCode.NoContent, response.status)

        val result = response.bodyAsText()

        val dto = json.decodeFromString<FinalServiceDTO>(result)
        assertAll(
            { assertEquals(createProduct.serviceEntity?.image, dto.image) },
            { assertEquals(createProduct.serviceEntity?.type, dto.type) }
        )
    }

    @Test
    @Order(8)
    fun testGetProductById() = testApplication {

        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
        }

        var response = client.get("/sales/product/1") {
            contentType(ContentType.Application.Json)
        }


        assertEquals(HttpStatusCode.NoContent, response.status)

        val result = response.bodyAsText()

        val dto = json.decodeFromString<ProductDTO>(result)
        assertAll(
            { assertEquals(createProduct.productEntity?.image, dto.image) },
            { assertEquals(createProduct.productEntity?.type, dto.type) }
        )
    }
}