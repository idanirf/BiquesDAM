package biques.dam.es

import io.ktor.server.application.*
import biques.dam.es.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
   // configureSecurity()
    configureSerialization()
    configureRouting()
    configureValidation()

}
