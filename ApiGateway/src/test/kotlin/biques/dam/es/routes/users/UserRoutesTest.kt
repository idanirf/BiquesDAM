package biques.dam.es.routes.users

import biques.dam.es.dto.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.ktor.util.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.*
import java.util.*
import kotlin.test.assertEquals

val json = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserRoutesTest {
    private val config = ApplicationConfig("application.conf")

    val responseDTO = UserResponseDTO(
        id = 1,
        uuid = "41152394-fb3f-44ee-bb6c-be4fc4af8bf1",
        image = "Test",
        email = "Test",
        username = "Test",
        rol = setOf("ADMIN"),
        address = "address"
    )

    val updateDTO = UserUpdateDTO(
        image = "Test",
        email = "Test",
        username = "Test",
        password = "Test",
        rol = setOf("ADMIN"),
        address = "address"
    )

    val registerDTO = UserRegisterDTO(
        image = "Test",
        email = "Test",
        username = "Test",
        password = "Test",
        rol = setOf("ADMIN"),
        address = "address"
    )

    val loginDTO = UserLoginDTO(
        username = "admin",
        password = "admin1234"
    )

    @Test
    @Order(1)
    fun register() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/users/register") {
            contentType(ContentType.Application.Json)
            setBody(registerDTO)
        }

        assertEquals(response.status, HttpStatusCode.Created)
    }

    @Test
    @Order(2)
    fun login() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/users/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDTO)
        }

        assertEquals(response.status, HttpStatusCode.OK)
    }

    @OptIn(InternalAPI::class)
    @Test
    @Order(3)
    fun findAll() = testApplication {
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


        val response = client.get("/users") {
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(4)
    fun findById() = testApplication {
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

        val response = client.get("/users/1") {
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }

        assertEquals(response.status, HttpStatusCode.OK)
    }

    @Test
    @Order(5)
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

        val response = client.put("/users/1") {
            contentType(ContentType.Application.Json)
            setBody(updateDTO)
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }


        assertEquals(response.status, HttpStatusCode.OK)
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

        val response = client.delete("/users/1") {
            header(HttpHeaders.Authorization, "Bearer " + dto.token)
        }

        assertEquals(response.status, HttpStatusCode.NoContent)
    }
}