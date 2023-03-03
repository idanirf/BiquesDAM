package es.dam.biques.microserviciousuarios

import es.dam.biques.microserviciousuarios.db.getUsersInit
import es.dam.biques.microserviciousuarios.service.UserService
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableCaching
@EnableR2dbcRepositories
class MicroservicioUsuariosApplication
@Autowired constructor(
    private val usersService: UserService
) : CommandLineRunner {
    override fun run(vararg args: String?) = runBlocking {
        getUsersInit().forEach {
            usersService.save(it)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<MicroservicioUsuariosApplication>(*args)
}
