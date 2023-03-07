package biques.dam.es.services.users

import biques.dam.es.dto.*
import de.jensklingenberg.ktorfit.http.*
import io.ktor.server.auth.jwt.*

/**
 * Interface that defines the methods to communicate with the server for the Users API
 * @author The BiquesDAM Team
 */
interface KtorFitRestUsers {

    /**
     * Gets all the users
     * @param token The token of the user
     * @return A list of UserDataDTO
     * @author The BiquesDAM Team
     */
    @GET("users")
    suspend fun findAll(
        @Header("Authorization") token: String
    ): UserDataDTO

    /**
     * Gets a user by its id
     * @param token The token of the user
     * @param id The id of the user
     * @return The UserResponseDTO with the given id
     * @author The BiquesDAM Team
     */
    @GET("users/{id}")
    suspend fun findById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): UserResponseDTO

    /**
     * Method to log in a user
     * @return The UserTokenDTO
     * @author The BiquesDAM Team
     */
    @POST("users/login")
    suspend fun login(
        @Body user: UserLoginDTO
    ): UserTokenDTO

    /**
     * Method to register a user
     * @return The UserTokenDTO
     * @author The BiquesDAM Team
     */
    @POST("users/register")
    suspend fun register(
        @Body user: UserRegisterDTO
    ): UserTokenDTO

    /**
     * Method to update a user
     * @param token The token of the user
     * @param id The id of the user
     * @param user The UserUpdateDTO with the new data
     * @return The UserResponseDTO with the updated data
     * @author The BiquesDAM Team
     */
    @PUT("users/{id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Path("id") id: Long, @Body user: UserUpdateDTO
    ): UserResponseDTO

    /**
     * Method to delete a user
     * @param token The token of the user
     * @param id The id of the user to delete
     * @author The BiquesDAM Team
     */
    @DELETE("users/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    )
}