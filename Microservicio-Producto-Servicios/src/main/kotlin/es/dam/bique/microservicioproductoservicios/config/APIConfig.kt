package es.dam.bique.microservicioproductoservicios.config

import org.springframework.context.annotation.Configuration

@Configuration
class APIConfig {
    companion object {
        const val API_VERSION = "1.0"
        const val API_PATH = "/prueba"
    }
}