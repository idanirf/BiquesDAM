package es.dam.biques.microserviciousuarios.dto

import es.dam.biques.microserviciousuarios.models.TipoUsuario
import java.time.LocalDateTime
import java.util.*

data class UsuarioDTO(
    val id: Long?,
    val uuid: UUID = UUID.randomUUID(),
    val imagen: String?,
    val tipo: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val direccion: String,
    val metadata: Metadata
) {
    data class Metadata(
        val createdAt: LocalDateTime? = LocalDateTime.now(),
        val updatedAt: LocalDateTime? = LocalDateTime.now(),
        val deleted: Boolean = false
    )
}

data class UsuarioCreateDTO(
    val imagen: String? = null,
    val tipo: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val direccion: String
)

data class UsuarioUpdateDTO(
    val email: String,
    val username: String,
)

data class UsuarioLoginDTO(
    val username: String,
    val password: String
)

data class UsuarioTokenDTO(
    val user: UsuarioDTO,
    val token: String
)