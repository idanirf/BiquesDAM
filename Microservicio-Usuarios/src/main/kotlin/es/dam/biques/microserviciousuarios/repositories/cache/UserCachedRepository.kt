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

    @Cacheable("users")
    override suspend fun findById(id: Long): User? = withContext(Dispatchers.IO) {
        logger.info { "findById($id)" }

        return@withContext usersRepository.findById(id)
    }

    @Cacheable("users")
    override suspend fun findByEmail(email: String): User? = withContext(Dispatchers.IO) {
        logger.info { "findByEmail($email)" }

        return@withContext usersRepository.findUserByEmail(email).firstOrNull()
    }

    @CachePut("users")
    override suspend fun save(user: User): User = withContext(Dispatchers.IO) {
        logger.info { "save($user)" }

        val saved = user.copy(
            uuid = user.uuid,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return@withContext usersRepository.save(user)
    }

    @Cacheable("users")
    override suspend fun update(id: Long, user: User): User? = withContext(Dispatchers.IO) {
        logger.info { "update($id, $user)" }

        val userDB = usersRepository.findById(id)
        userDB?.let {
            val updated = it.copy(
                uuid = it.uuid,
                image = it.image,
                type = it.type,
                email = it.email,
                username = it.username,
                password = it.password,
                address = it.address,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                deleted = it.deleted,
                lastPasswordChangeAt = it.lastPasswordChangeAt
            )

            return@withContext usersRepository.save(updated)
        }

        return@withContext null
    }

    @CacheEvict("users", allEntries = true)
    override suspend fun delete(user: User) = withContext(Dispatchers.IO) {
        logger.info { "deleteAll()" }

        return@withContext usersRepository.deleteAll()
    }

    @CacheEvict("users")
    override suspend fun deleteById(id: Long) = withContext(Dispatchers.IO) {
        logger.info { "deleteById($id)" }

        return@withContext usersRepository.deleteById(id)
    }

}