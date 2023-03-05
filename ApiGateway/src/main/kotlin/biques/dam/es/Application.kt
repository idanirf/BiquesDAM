package biques.dam.es

import io.ktor.server.application.*
import biques.dam.es.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureKoin()

    configureSecurity()

    configureSerialization()

    configureSockets()

    configureHTTP()

    configureSwagger()

    configureRouting()
}
