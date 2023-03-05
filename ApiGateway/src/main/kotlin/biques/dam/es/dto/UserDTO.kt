package biques.dam.es.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterDTO(
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

@Serializable
data class UserUpdateDTO(
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

@Serializable
data class UserLoginDTO(
    val username: String,
    val password: String
)

@Serializable
data class UserResponseDTO(
    val id: Long?,
    val uuid: String,
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val address: String
)

@Serializable
data class UserTokenDTO(
    val user: UserResponseDTO,
    val token: String
)