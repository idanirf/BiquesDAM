package biques.dam.es.routes.sales

import biques.dam.es.dto.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.*
import kotlin.test.assertEquals

/*private val json = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SalesRoutesTest {

    private val appointment = AppointmentDTO(
        id = 1L,
        uuid = "e8c8a17a-25e7-41c5-9de9-265923c14483",
        user = "e8c8a17a-25e7-41c5-9de9-265923c14482",
        assistance = "ANY",
        date = "2023-03-03",
        description = "test"
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

    private val createService = SaleCreateDTO(
        productEntity = null,
        serviceEntity = ServiceCreateDTO(
            image = "create",
            price = 0.0f,
            appointment = appointment.uuid,
            type = "REVISION"
        ),
        type = "SERVICE"
    )

    private val createAppointment = AppointmentCreateDTO(
        userId = "e8c8a17a-25e7-41c5-9de9-265923c14482",
        assistance = "ANY",
        date = "2023-03-03",
        description = "test"
    )

    private val loginDTO = UserLoginDTO(
        username = "admin",
        password = "admin1234"
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

        val dto = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        val response = client.get("/sales"){
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }

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

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val response = client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createService)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }



        val dto = json.decodeFromString<FinalSaleDTO>(response.bodyAsText())

        assertAll(
            { assertEquals(HttpStatusCode.Created, response.status) },
            { assertEquals(createService.serviceEntity?.image, dto.serviceEntity!!.image) },
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

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val response = client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val dto = json.decodeFromString<SaleDTO>(response.bodyAsText())

        assertAll(
            { assertEquals(HttpStatusCode.Created, response.status) },
            { assertEquals(createService.productEntity?.image, dto.productEntity!!.image) },
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

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val response = client.put("/sales/1") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
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

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val response = client.put("/sales/1") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
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

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        val response = client.delete("/sales/1") {
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

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

        val login = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        client.post("/sales/appointments") {
            contentType(ContentType.Application.Json)
            setBody(createAppointment)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        client.post("/sales") {
            contentType(ContentType.Application.Json)
            setBody(createProduct)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)

        }

        val response = client.get("/sales/service/1") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)

        }

        val dto = json.decodeFromString<FinalServiceDTO>(response.bodyAsText())

        assertAll(
            { assertEquals(HttpStatusCode.NoContent, response.status) },
            { assertEquals(createProduct.serviceEntity?.image, dto.image) },
            { assertEquals(createProduct.serviceEntity?.type, dto.type) }
        )
    }
}

 */