package es.dam.biques.microserviciousuarios.service

import es.dam.biques.microserviciousuarios.config.websocket.ServerWebSocketConfig
import es.dam.biques.microserviciousuarios.config.websocket.WebSocketHandler
import es.dam.biques.microserviciousuarios.exceptions.UserNotFoundException
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserService
@Autowired constructor(
    private val usersRepository: UsersRepository,
    private val serverWebSocketConfig: ServerWebSocketConfig
) : IUsersService {
    private val webSocketService = serverWebSocketConfig.webSocketUsersHandler() as WebSocketHandler
    override suspend fun findAll(): Flow<User> {
        logger.info { "findAll()" }

        return usersRepository.findAll()
    }

    override suspend fun findById(id: Long): User? {
        logger.info { "findById($id)" }

        return usersRepository.findById(id) ?: throw UserNotFoundException("User $id not found.")
    }

    override suspend fun findByEmail(email: String): User? {
        logger.info { "findByEmail($email)" }

        return usersRepository.findUserByEmail(email).firstOrNull()
    }

    override suspend fun save(user: User): User {
        logger.info { "save($user)" }

        // TODO: Pensar si hay que añadir el onChange()
        return usersRepository.save(user)
    }

    override suspend fun update(id: Long, user: User): User? {
        logger.info { "update($id, $user)" }

        return usersRepository.save(user) ?: throw UserNotFoundException("User $id not found.")
    }

    override suspend fun delete(user: User) {
        logger.info { "delete($user)" }

        usersRepository.findById(user.id!!).let {
            usersRepository.delete(user)
        }
    }

    override suspend fun deleteById(id: Long) {
        logger.info { "deleteById($id)" }

        usersRepository.deleteById(id)
    }
}