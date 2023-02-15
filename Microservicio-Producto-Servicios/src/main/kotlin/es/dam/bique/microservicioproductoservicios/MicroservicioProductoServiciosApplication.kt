package es.dam.bique.microservicioproductoservicios

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class MicroservicioProductoServiciosApplication

fun main(args: Array<String>) {
    runApplication<MicroservicioProductoServiciosApplication>(*args)
}
