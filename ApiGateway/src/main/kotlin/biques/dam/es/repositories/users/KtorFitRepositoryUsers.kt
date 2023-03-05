package biques.dam.es.repositories.users

import biques.dam.es.dto.*
import biques.dam.es.dto.UserResponseDTO
import biques.dam.es.exceptions.UserBadRequestException
import biques.dam.es.exceptions.UserNotFoundException
import biques.dam.es.services.users.KtorFitClientUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger {}

@Single
@Named("KtorFitRepositoryUsers")
class KtorFitRepositoryUsers: IUsersRepository {
    private val client by lazy { KtorFitClientUsers.instance }

    override suspend fun findAll(token: String): Flow<UserResponseDTO> = withContext(Dispatchers.IO) {
        logger.debug { "findAll()" }

        val call = client.findAll(token)
        try {
            return@withContext call.asFlow()
        } catch (e: Exception) {
            throw UserNotFoundException("Error getting users: ${e.message}")
        }
    }

    override suspend fun findById(token: String, id: Long): UserResponseDTO {
        logger.debug { "finById($id)" }

        val call = client.findById(token, id)

        try {
            return call
        } catch (e: Exception) {
            throw UserNotFoundException("Error getting user with id $id ${e.message}")
        }
    }

    override suspend fun login(entity: UserLoginDTO): UserTokenDTO {
        logger.debug { "login($entity)" }

        val call = client.login(entity)

        try {
            return call
        } catch (e: Exception) {
            throw UserBadRequestException("Error logging user: ${e.message}")
        }
    }

    override suspend fun register(entity: UserRegisterDTO): UserTokenDTO {
        logger.debug { "register($entity)" }

        val call = client.register(entity)

        try {
            return call
        } catch (e: Exception) {
            throw UserBadRequestException("Error registering user: ${e.message}")
        }
    }

    override suspend fun update(token: String, id: Long, entity: UserUpdateDTO): UserResponseDTO {
        logger.debug { "update($entity)" }

        try {
            return client.update(token, id, entity)
        } catch (e: Exception) {
            throw UserNotFoundException("Error finding user with id $id: ${e.message}")
        }
    }

    override suspend fun delete(token: String, id: Long) {
        logger.debug { "delete($id)" }

        try {
            client.delete(token, id)
        } catch (e: Exception) {
            throw UserNotFoundException("Error deleting user with id $id: ${e.message}")
        }
    }
}