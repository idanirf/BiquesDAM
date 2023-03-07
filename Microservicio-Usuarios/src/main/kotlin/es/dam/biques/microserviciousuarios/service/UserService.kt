package es.dam.biques.microserviciousuarios.service

import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException
import es.dam.biques.microserviciousuarios.exceptions.UserNotFoundException
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.repositories.UsersRepository
import es.dam.biques.microserviciousuarios.repositories.cache.UserCachedRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
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
    private val userCachedRepository: UserCachedRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    /**
     * Suspend function that creates a new user.
     * @param userDTO the user to be created
     * @return the user created
     * @throws UserBadRequestException if the user already exists
     * @author BiquesDAM-Team
     */
    override fun loadUserByUsername(username: String?): UserDetails = runBlocking {
        return@runBlocking usersRepository.findUserByUsername(username!!).firstOrNull()
            ?: throw UserNotFoundException("User doesn't found with username: $username")
    }

    /**
     * Suspend function that finds all users.
     * @return the list of users found
     * @author BiquesDAM-Team
     */
    suspend fun findAll() = withContext(Dispatchers.IO) {
        logger.info { "findAll()" }

        return@withContext userCachedRepository.findAll()
    }

    /**
     * Suspend function that finds a user by id.
     * @param id the id of the user to be found
     * @return the user found
     * @throws UserNotFoundException if the user doesn't exist
     * @author BiquesDAM-Team
     */
    @Cacheable("USERS")
    suspend fun findUserById(id: Long) = withContext(Dispatchers.IO) {
        return@withContext userCachedRepository.findById(id)
            ?: throw UserNotFoundException("User with id $id not found.")
    }

//    @Cacheable("USERS")
//    suspend fun findUserByUuid(uuid: UUID) = withContext(Dispatchers.IO) {
//        return@withContext userCachedRepository.findByUUID(uuid)
//            ?: throw UserNotFoundException("User with uuid $uuid not found.")
//    }

    /**
     * Suspend function that creates a new user.
     * @param user the user to be created
     * @return the created user
     * @throws UserBadRequestException if the user already exists
     * @author BiquesDAM-Team
     */
    suspend fun save(user: User, isAdmin: Boolean = false): User = withContext(Dispatchers.IO) {
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

        val saved = user.copy(
            password = passwordEncoder.encode(user.password),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        try {
            return@withContext userCachedRepository.save(saved)
        } catch (e: Exception) {
            throw UserBadRequestException("Error creating the user. -> ${e.message}")
        }
    }

    /**
     * Suspend function that updates a user with the given ID.
     * @param id the ID of the user to be updated
     * @param user the user to be updated
     * @return the updated user if it exists, otherwise throw a [UserNotFoundException]
     * @throws [UserBadRequestException] if the user is not valid
     * @throws [UserNotFoundException] if the user with the given ID does not exist
     * @author BiquesDAM-Team
     */
    suspend fun update(id: Long, user: User): User? = withContext(Dispatchers.IO) {
        try {
            return@withContext userCachedRepository.update(id, user)
        } catch (e: UserBadRequestException) {
            throw UserBadRequestException("Error updating the user.")
        } catch (ex: UserNotFoundException) {
            throw UserNotFoundException("User with id $id not found.")
        }
    }

    /**
     * Suspend function that deletes a user with the given ID.
     * @param id the ID of the user to be deleted
     * @return the deleted user if it exists, otherwise throw a [UserNotFoundException]
     * @throws [UserNotFoundException] if the user with the given ID does not exist
     * @author BiquesDAM-Team
     */
    suspend fun deleteById(id: Long): User? = withContext(Dispatchers.IO) {
        return@withContext userCachedRepository.deleteById(id)
            ?: throw UserNotFoundException("User with id $id not found.")
    }
}