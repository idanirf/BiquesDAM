package biques.dam.es.repositories.users

import biques.dam.es.dto.*
import biques.dam.es.dto.UserResponseDTO
import biques.dam.es.exceptions.OrderNotFoundException
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

/**
 * Implementation of the IUsersRepository interface.
 * @author BiquesDAM-Team
 */

private val logger = KotlinLogging.logger {}

@Single
@Named("KtorFitRepositoryUsers")
class KtorFitRepositoryUsers: IUsersRepository {
    private val client by lazy { KtorFitClientUsers.instance }

    /**
     * Returns a List of all users.
     * @param token the access token for authentication.
     * @return a list of all users in the database.
     * @throws UserNotFoundException if the users are not found.
     */
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

    /**
     * Finds a user by its ID.
     * @param token the access token for authentication.
     * @param id the ID of the user to find.
     * @return the UserResponseDTO object
     * @throws UserNotFoundException if the user is not found.
     */

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

    /**
     * Login a user.
     * @param entity the UserLoginDTO object.
     * @return the UserTokenDTO object.
     */
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

    /**
     * Register a user.
     * @param entity the UserRegisterDTO object.
     * @return the UserTokenDTO object.
     */
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

    /**
     * Updates an existing user with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the user to update.
     * @param entity the new user data to update.
     * @return the updated user object.
     * @throws UserNotFoundException if the user to update is not found.
     */
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

    /**
     * Delete a user with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the user to delete.
     * @throws UserNotFoundException if the user to delete is not found.
     */
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