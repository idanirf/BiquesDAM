package biques.dam.es.repositories.users

import biques.dam.es.dto.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.flow.Flow

/**
 * This interface represents the repository for the User model.
 * @author BiquesDAM-Team
 */
interface IUsersRepository {
    /**
     * Find all users
     * @param token JWT token
     * @return Flow of UserResponseDTO
     */
    suspend fun findAll(token: String): List<UserResponseDTO>
    /**
     * Find user by id
     * @param token JWT token
     * @param id User id
     * @return UserResponseDTO
     */
    suspend fun findById(token: String, id: Long): UserResponseDTO

    /**
     * Login user
     * @param entity UserLoginDTO
     * @return UserTokenDTO
     */
     suspend fun login(entity: UserLoginDTO): UserTokenDTO
     /**
      * Register user
      * @param entity UserRegisterDTO
      * @return UserTokenDTO
      */
    suspend fun register(entity: UserRegisterDTO): UserTokenDTO
    /**
     * Update a user
     * @param token JWT String
     * @param entity UserUpdateDTO
     * @param id Long id
     * @return UserResponseDTO
     */
    suspend fun update(token: String, id: Long, entity: UserUpdateDTO): UserResponseDTO
    /**
     * Delete a user
     * @param token JWT String
     * @param id Long id
     */
    suspend fun delete(token: String, id: Long)
}
