package dam.biques.es

import io.ktor.server.application.*
import dam.biques.es.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSockets()
    configureSerialization()
    configureRouting()
}
