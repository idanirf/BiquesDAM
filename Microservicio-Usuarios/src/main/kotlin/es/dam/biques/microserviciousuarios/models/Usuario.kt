package es.dam.biques.microserviciousuarios.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "usuarios")
data class Usuario(
    @Id
    val id: Long? = null,
    val uuid: UUID = UUID.randomUUID(),
    val imagen: String? = null,
    @Column("rol")
    val tipo: String,
    val email: String,
    @get:JvmName("userName")
    val username: String,
    @get:JvmName("userPassword")
    val password: String,
    val direccion: String,

    // Históricos.
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column("updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
    @Column("last_password_change_at")
    val lastPasswordChangeAt: LocalDateTime = LocalDateTime.now()
)

enum class TipoUsuario {
    SUPERADMIN, ADMIN, CLIENTE
}