package es.dam.biques.microserviciousuarios.dto


import es.dam.biques.microserviciousuarios.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserDTO(
    val id: Long?,
    val uuid: String,
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val address: String,
    val metadata: Metadata
) {
    @Serializable
    data class Metadata(
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime? = LocalDateTime.now(),
        @Serializable(with = LocalDateTimeSerializer::class)
        val updatedAt: LocalDateTime? = LocalDateTime.now()
    )
}

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