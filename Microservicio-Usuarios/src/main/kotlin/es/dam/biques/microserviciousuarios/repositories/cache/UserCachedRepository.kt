package es.dam.biques.microserviciousuarios.repositories.cache

import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger {}

@Repository
class UserCachedRepository
@Autowired constructor(
    private val usersRepository: UsersRepository
) : IUserCachedRepository {

    /**
     * Retrieves all users from the database.
     * @return a Flow of User objects
     * @throws RuntimeException if there is an error while accessing the database
     * @author BiquesDAM-Team
     */
    override suspend fun findAll(): Flow<User> = withContext(Dispatchers.IO) {
        logger.info { "findAll()" }

        return@withContext usersRepository.findAll()
    }

    /**
     * Finds a user by their ID.
     * @param id the ID of the user to find
     * @return the User object if found, or null if not found
     * @throws RuntimeException if there is an error while accessing the database
     * @author BiquesDAM-Team
     */
    @Cacheable("USERS")
    override suspend fun findById(id: Long): User? = withContext(Dispatchers.IO) {
        logger.info { "findById($id)" }

        return@withContext usersRepository.findById(id)
    }

    /**
     * Finds a user by their email address.
     * @param email the email address of the user to find
     * @return the User object if found, or null if not found
     * @throws RuntimeException if there is an error while accessing the database
     * @author BiquesDAM-Team
     */
    @Cacheable("USERS")
    override suspend fun findByEmail(email: String): User? = withContext(Dispatchers.IO) {
        logger.info { "findByEmail($email)" }

        return@withContext usersRepository.findUserByEmail(email).firstOrNull()
    }

    /**
     * Finds a user by UUID and caches the result with the "USERS" key.
     * @param uuid the UUID of the user to find.
     * @return the user with the specified UUID, or null if no user was found.
     * @author BiquesDAM-Team
     */
    @Cacheable("USERS")
    override suspend fun findByUUID(uuid: String): User? = withContext(Dispatchers.IO) {
        logger.info { "findByUUID($uuid" }

        return@withContext usersRepository.findUserByUuid(uuid).firstOrNull()
    }

    /**
     * Saves a new user and clears the "USERS" cache.
     * @param user the user object to save.
     * @return the saved user object.
     * @author BiquesDAM-Team
     */
    @CachePut("USERS")
    override suspend fun save(user: User): User = withContext(Dispatchers.IO) {
        logger.info { "save($user)" }

        val saved = user.copy(
            id = user.id,
            uuid = user.uuid,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return@withContext usersRepository.save(user)
    }

    /**
     * Updates a user with the specified ID and clears the "USERS" cache.
     * @param id the ID of the user to update.
     * @param user the updated user object.
     * @return the updated user, or null if no user with the specified ID was found.
     * @author BiquesDAM-Team
     */
    @CachePut("USERS")
    override suspend fun update(id: Long, user: User): User? = withContext(Dispatchers.IO) {
        logger.info { "Updating user: $user" }

        val userDB = usersRepository.findUserByUsername(user.username).firstOrNull()

        userDB?.let {
            val updtatedUser = user.copy(
                id = user.id,
                updatedAt = LocalDateTime.now()
            )

            return@withContext usersRepository.save(updtatedUser)
        }

        return@withContext null
    }

    /**
     *  Deletes a user by ID and clears the "USERS" cache.
     *  @param id the ID of the user to delete.
     *  @return the deleted user, or null if no user with the specified ID was found.
     *  @author BiquesDAM-Team
     */
    @CacheEvict("USERS")
    override suspend fun deleteById(id: Long): User? = withContext(Dispatchers.IO) {
        logger.info { "deleteById($id)" }

        val user = findById(id)
        user?.let {
            usersRepository.deleteById(id)
            return@withContext user
        }

        return@withContext null
    }
}