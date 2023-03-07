package biques.dam.es.routes.orders

import biques.dam.es.dto.FinalOrderDTO
import biques.dam.es.dto.ProductDTO
import biques.dam.es.dto.UserLoginDTO
import biques.dam.es.dto.UserTokenDTO
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
class OrdersRoutesTest {
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

        val dtoToken = json.decodeFromString<UserTokenDTO>(login.bodyAsText())

        val response = client.get("/orders"){
            header(HttpHeaders.Authorization, "Bearer " + dtoToken.token)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }
}
 */
