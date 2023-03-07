package biques.dam.es.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

/**
 * Plugin to configure the Swagger UI
 * @author The BiquesDAM Team
 */
fun Application.configureSwagger() {

    install(SwaggerUI) {

        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }

        info {
            title = "BIQUESDAM API REST"
            version = "latest"
            description = "Final project for the PSP and AD modules."
            contact {
                name = "BIQUESDAM"
                url = "https://github.com/idanirf/BiquesDAM"
            }
        }

        server {
            url = environment.config.property("server.baseSecureUrl").getString()
            description = "API Gateway that connects every microservice."
        }

        schemasInComponentSection = true
        examplesInComponentSection = true
        automaticTagGenerator = { url -> url.firstOrNull() }
        pathFilter = { method, url ->
            url.contains("test")
        }

        securityScheme("JWT-Auth") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
    }
}