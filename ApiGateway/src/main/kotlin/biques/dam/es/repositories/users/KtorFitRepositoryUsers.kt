package biques.dam.es.repositories.users

import biques.dam.es.dto.*
import biques.dam.es.dto.UserResponseDTO
import biques.dam.es.exceptions.UserBadRequestException
import biques.dam.es.exceptions.UserNotFoundException
import biques.dam.es.services.users.KtorFitClientUsers
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger {}

@Single
@Named("KtorFitRepositoryUsers")
class KtorFitRepositoryUsers: IUsersRepository {
    private val client by lazy { KtorFitClientUsers.instance }

    override suspend fun findAll(token: String): List<UserResponseDTO> = withContext(Dispatchers.IO) {
        logger.debug { "findAll()" }

        val call = async {
            client.findAll(token)
        }

        try {
            return@withContext call.await().data!!
        } catch (e: Exception) {
            throw UserNotFoundException("Error getting users: ${e.message}")
        }
    }

    override suspend fun findById(token: String, id: Long): UserResponseDTO = withContext(Dispatchers.IO) {
        logger.debug { "finById($id)" }

        val call = async {
            client.findById(token, id)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw UserNotFoundException("Error getting user with id $id ${e.message}")
        }
    }

    override suspend fun login(entity: UserLoginDTO): UserTokenDTO = withContext(Dispatchers.IO) {
        logger.debug { "login($entity)" }

        val call = async {
            client.login(entity)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw UserBadRequestException("Error logging user: ${e.message}")
        }
    }

    override suspend fun register(entity: UserRegisterDTO): UserTokenDTO = withContext(Dispatchers.IO) {
        logger.debug { "register($entity)" }

        val call = async {
            client.register(entity)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw UserBadRequestException("Error registering user: ${e.message}")
        }
    }

    override suspend fun update(token: String, id: Long, entity: UserUpdateDTO): UserResponseDTO = withContext(Dispatchers.IO) {
        logger.debug { "update($entity)" }

        val call = async {
            client.update(token, id, entity)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw UserNotFoundException("Error finding user with id $id: ${e.message}")
        }
    }

    override suspend fun delete(token: String, id: Long) = withContext(Dispatchers.IO) {
        logger.debug { "delete($id)" }

        val call = async {
            client.delete(token, id)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw UserNotFoundException("Error deleting user with id $id: ${e.message}")
        }
    }
}