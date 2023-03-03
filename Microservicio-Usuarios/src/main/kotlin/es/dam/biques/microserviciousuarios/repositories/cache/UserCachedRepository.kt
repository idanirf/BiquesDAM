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
    override suspend fun findAll(): Flow<User> = withContext(Dispatchers.IO) {
        logger.info { "findAll()" }

        return@withContext usersRepository.findAll()
    }

    @Cacheable("USERS")
    override suspend fun findById(id: Long): User? = withContext(Dispatchers.IO) {
        logger.info { "findById($id)" }

        return@withContext usersRepository.findById(id)
    }

    @Cacheable("USERS")
    override suspend fun findByEmail(email: String): User? = withContext(Dispatchers.IO) {
        logger.info { "findByEmail($email)" }

        return@withContext usersRepository.findUserByEmail(email).firstOrNull()
    }

    @Cacheable("USERS")
    override suspend fun findByUUID(uuid: UUID): User? = withContext(Dispatchers.IO) {
        logger.info { "findByUUID($uuid" }

        return@withContext usersRepository.findUserByUuid(uuid).firstOrNull()
    }

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

    @CacheEvict("USERS")
    override suspend fun delete(user: User) = withContext(Dispatchers.IO) {
        logger.info { "deleteAll()" }

        return@withContext usersRepository.deleteAll()
    }

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