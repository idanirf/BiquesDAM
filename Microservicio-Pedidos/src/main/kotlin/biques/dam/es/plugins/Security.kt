package biques.dam.es.plugins

import biques.dam.es.config.TokenConfig
import biques.dam.es.services.jwt.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

/**
 * Configures JWT authentication with the provided token config params and JWT service.
 *
 * @throws ConfigurationException if the configuration properties for JWT are missing or invalid
 */
fun Application.configureSecurity() {

    val tokenConfigParams = mapOf<String, String>(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "issuer" to environment.config.property("jwt.issuer").getString(),
        "realm" to environment.config.property("jwt.realm").getString()
    )

    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParams) }

    val jwtService: TokenService by inject()

    authentication {
        jwt {
            verifier(jwtService.verifyToken())
            realm = tokenConfig.realm
            validate { credential ->
                JWTPrincipal(credential.payload)
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
            }
        }
    }
}
