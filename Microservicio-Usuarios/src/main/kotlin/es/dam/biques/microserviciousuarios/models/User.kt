package es.dam.biques.microserviciousuarios.models

import es.dam.biques.microserviciousuarios.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*

@Table(name = "USERS")
@Serializable
data class User(
    @Id
    val id: Long? = null,
//    @Serializable(with = UUIDSerializer::class)
    val uuid: String = UUID.randomUUID().toString(),
    val image: String? = null,
    @Column("rol")
    val rol: String = User.TipoUsuario.CLIENT.name,
    val email: String,
    @get:JvmName("userName")
    val username: String,
    @get:JvmName("userPassword")
    val password: String,
    val address: String,

    // Históricos.
    @Column("created_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column("updated_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column("last_password_changed_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastPasswordChangeAt: LocalDateTime = LocalDateTime.now()
) : UserDetails {

    /**
     *  Extension function that converts a [User] object into a [UserResponseDTO] object.
     *  @return the converted [UserResponseDTO] object
     *  @author BiquesDAM-Team
     */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return rol.split(",").map { SimpleGrantedAuthority("ROLE_${it.trim()}") }.toMutableList()
    }

    /**
     * Returns the password string.
     * @return the password string.
     * @author BiquesDAM-Team
     */
    override fun getPassword(): String {
        return password
    }

    /**
     * Returns the username string.
     * @return the username string. Cannot be null or empty string.
     * @author BiquesDAM-Team
     */
    override fun getUsername(): String {
        return username
    }

    /**
     * Returns true if the user's account has expired, false otherwise.
     * @return true if the user's account has expired, false otherwise. The default value is false.
     * @author BiquesDAM-Team
     */
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    /**
     * Returns true if the user is locked or disabled, false otherwise.
     * @return true if the user is locked or disabled, false otherwise. The default value is false.
     * @author BiquesDAM-Team
     */
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    /**
     * Returns true if the user's credentials (password) has expired, false otherwise.
     * @return true if the user's credentials (password) has expired, false otherwise. The default value is false.
     * @author BiquesDAM-Team
     */
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    /**
     * Returns true if the user is enabled, false otherwise.
     * @return true if the user is enabled, false otherwise. The default value is false.
     * @author BiquesDAM-Team
     */
    override fun isEnabled(): Boolean {
        return true
    }

    /**
     * Enum class that represents the different types of users.
     * @property SUPERADMIN the superadmin user type
     * @property ADMIN the admin user type
     * @property CLIENT the client user type
     * @author BiquesDAM-Team
     */
    enum class TipoUsuario {
        SUPERADMIN, ADMIN, CLIENT
    }
}