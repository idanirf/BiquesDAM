package biques.dam.es.plugins

import io.ktor.server.application.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

/**
 * Plugin to configure the Koin dependency injection
 * @author The BiquesDAM Team
 */
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        defaultModule()
    }
}