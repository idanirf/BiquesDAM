package es.dam.biques.microserviciousuarios

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class MicroservicioUsuariosApplication

fun main(args: Array<String>) {
    runApplication<MicroservicioUsuariosApplication>(*args)
}
