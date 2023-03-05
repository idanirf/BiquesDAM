package biques.dam.es.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "BIQUESDAM API REST"
            version = "latest"
            description = "Proyecto final mixto para los módulos de AD y PSP."
            contact {
                name = "BIQUESDAM"
                url = "https://github.com/idanirf/BiquesDAM"
            }
        }
        server {
            url = environment.config.property("server.baseSecureUrl").getString()
            description = "API Gateway que conecta todos los microservicios."
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