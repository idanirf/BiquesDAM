package es.dam.biques.microserviciousuarios.dto

import java.time.LocalDateTime
import java.util.*

data class UserDTO(
    val id: Long?,
    val uuid: UUID = UUID.randomUUID(),
    val image: String?,
    val type: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String,
    val metadata: Metadata
) {
    data class Metadata(
        val createdAt: LocalDateTime? = LocalDateTime.now(),
        val updatedAt: LocalDateTime? = LocalDateTime.now(),
        val deleted: Boolean = false
    )
}

data class UserCreateDTO(
    val image: String?,
    val type: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

data class UserUpdateDTO(
    val image: String?,
    val type: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

data class UserLoginDTO(
    val username: String,
    val password: String
)

data class UserTokenDTO(
    val user: UserDTO,
    val token: String
)