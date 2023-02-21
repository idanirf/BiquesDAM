package es.dam.biques.microserviciousuarios.service

import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException
import es.dam.biques.microserviciousuarios.exceptions.UserNotFoundException
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class UserService
@Autowired constructor(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails = runBlocking {
        return@runBlocking usersRepository.findUserByUsername(username!!).firstOrNull()
            ?: throw UserNotFoundException("Usuario no encontrado con username: $username")
    }

    suspend fun findAll() = withContext(Dispatchers.IO) {
        logger.info { "findAll()" }

        return@withContext usersRepository.findAll()
    }

    @Cacheable("users")
    suspend fun findUserById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext usersRepository.findById(id)
    }

    @Cacheable("users")
    suspend fun findUserByUuid(uuid: UUID) = withContext(Dispatchers.IO) {
        return@withContext usersRepository.findUserByUuid(uuid).firstOrNull()
    }

    suspend fun save(user: User, isAdmin: Boolean = false): User = withContext(Dispatchers.IO) {
        logger.info { "save($user)" }

        if (usersRepository.findUserByUsername(user.username)
                .firstOrNull() != null
        ) {
            logger.info { "User already exists." }
            throw UserBadRequestException("Username already exists.")
        }
        if (usersRepository.findUserByEmail(user.email)
                .firstOrNull() != null
        ) {
            logger.info { "Email already exists." }
            throw UserBadRequestException("Email already exists.")
        }

        logger.info { "User doesn't exist." }
        var saved = user.copy(
            uuid = UUID.randomUUID(),
            password = passwordEncoder.encode(user.password),
            type = User.TipoUsuario.CLIENT.name,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        if (isAdmin)
            saved = saved.copy(
                type = User.TipoUsuario.CLIENT.name,
            )

        try {
            return@withContext usersRepository.save(saved)
        } catch (e: Exception) {
            throw UserBadRequestException("Error creating the user: Username or email already exist.")
        }
    }

    suspend fun update(id: Long, user: User): User? = withContext(Dispatchers.IO) {
        logger.info { "update($id, $user)" }

        logger.info { "Updating user: $user" }

        var userDB = usersRepository.findUserByUsername(user.username)
            .firstOrNull()

        if (userDB != null && userDB.id != user.id) {
            throw UserBadRequestException("Username already exists.")
        }

        userDB = usersRepository.findUserByEmail(user.email)
            .firstOrNull()

        if (userDB != null && userDB.id != user.id) {
            throw UserBadRequestException("Email already exists.")
        }

        logger.info { "User doesn't exist." }

        val updtatedUser = user.copy(
            updatedAt = LocalDateTime.now()
        )

        try {
            return@withContext usersRepository.save(updtatedUser)
        } catch (e: Exception) {
            throw UserBadRequestException("Error updating user: Username or email already exist.")
        }
    }
}