package biques.dam.es.plugins

import biques.dam.es.config.TokenConfig
import biques.dam.es.services.jwt.TokensService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val jwtService: TokensService by inject()

    val tokenConfigParams = mapOf<String, String>(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "issuer" to environment.config.property("jwt.issuer").getString(),
    )

    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParams) }

//role
    authentication {
        jwt {
            verifier(jwtService.verifyToken())
            validate { credential ->
                if (credential.payload.audience.contains(tokenConfig.audience) && credential.payload.getClaim("username").asString().isNotEmpty()
                ){
                    JWTPrincipal(credential.payload)
                }
                else{
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token invalido o expirado")
            }
        }
    }
}
