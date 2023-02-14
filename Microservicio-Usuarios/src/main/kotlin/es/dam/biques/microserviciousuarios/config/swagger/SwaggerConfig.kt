package es.dam.biques.microserviciousuarios.config.swagger

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun apiInfo(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Documentación de Usuarios · Swagger")
                    .version("1.0.0")
                    .description("Proyecto final de Acceso a Datos y Programación de servicios y procesos.")
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Repositorio de GitHub del proyecto y su documentación")
                    .url("https://github.com/idanirf/BiquesDAM")
            )
    }
}