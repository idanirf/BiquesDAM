package biques.dam.es

import io.ktor.server.application.*
import biques.dam.es.plugins.*

/**
 * Main entry point of the application
 * @param args The arguments of the application
 * @author The BiquesDAM Team
 */
fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

/**
 * Defines the configuration of the application and the plugins to use
 * @author The BiquesDAM Team
 */
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
