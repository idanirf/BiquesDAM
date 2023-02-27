package es.dam.biques.microserviciousuarios.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class APIConfig {
    companion object {
        @Value("\${api.version}")
        const val API_VERSION = "1.0"

        @Value("\${project.name}")
        const val PROJECT_NAME = "BiquesDAM"
    }
}