package biques.dam.es.routes.users

import biques.dam.es.dto.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.*
import java.util.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import kotlin.test.assertEquals

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
        username = "Test",
        password = "Test"
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

    @Test
    @Order(3)
    fun findAll() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.get("/users")

        assertEquals(response.status, HttpStatusCode.OK)
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

        val response = client.get("/users/1")

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

        val response = client.put("/users/1") {
            contentType(ContentType.Application.Json)
            setBody(updateDTO)
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

        val response = client.delete("/users/1")

        assertEquals(response.status, HttpStatusCode.NoContent)
    }
}