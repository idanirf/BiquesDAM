package biques.dam.es.dto

data class UserRegisterDTO(
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

data class UserUpdateDTO(
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

data class UserLoginDTO(
    val username: String,
    val password: String
)

data class UserResponseDTO(
    val id: Long?,
    val uuid: String,
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val address: String
)

data class UserTokenDTO(
    val user: UserResponseDTO,
    val token: String
)