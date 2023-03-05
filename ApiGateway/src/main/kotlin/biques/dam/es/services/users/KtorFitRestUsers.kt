package biques.dam.es.services.users

import biques.dam.es.dto.*
import de.jensklingenberg.ktorfit.http.*

interface KtorFitRestUsers {
    @GET("users")
    suspend fun findAll(
        @Header("Authorization") token: String
    ): List<UserResponseDTO>

    @GET("users/{id}")
    suspend fun findById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): UserResponseDTO

    @POST("users/login")
    suspend fun login(
        @Body user: UserLoginDTO
    ): UserTokenDTO

    @POST("users/register")
    suspend fun register(
        @Body user: UserRegisterDTO
    ): UserTokenDTO

    @PUT("users/{id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Path("id") id: Long, @Body user: UserUpdateDTO
    ): UserResponseDTO

    @DELETE("users/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    )
}