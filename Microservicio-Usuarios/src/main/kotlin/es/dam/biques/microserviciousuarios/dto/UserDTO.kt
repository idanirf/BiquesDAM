package es.dam.biques.microserviciousuarios.dto


import es.dam.biques.microserviciousuarios.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Data transfer object used to serialize a [UserDTO] object
 * @property data [UserDTO] object to be serialized
 * @author BiquesDAM-Team
 */
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

/**
 * Data transfer object used to serialize a [UserDTO] object
 * @property data [UserDTO] object to be serialized
 * @author BiquesDAM-Team
 */
@Serializable
data class UserRegisterDTO(
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

/**
 * Data transfer object used to serialize a [UserDTO] object
 * @property data [UserDTO] object to be serialized
 * @author BiquesDAM-Team
 */
@Serializable
data class UserUpdateDTO(
    val image: String?,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

/**
 * Data transfer object used to serialize a [UserDTO] object
 * @property data [UserDTO] object to be serialized
 * @author BiquesDAM-Team
 */
@Serializable
data class UserLoginDTO(
    val username: String,
    val password: String
)

/**
 * Data transfer object used to serialize a [UserDTO] object
 * @property data [UserDTO] object to be serialized
 * @author BiquesDAM-Team
 */
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

/**
 * Data transfer object used to serialize a [UserResponseDTO] object and a [String] token
 * @property user [UserResponseDTO] object to be serialized
 * @property token [String] token to be serialized
 * @author BiquesDAM-Team
 */
@Serializable
data class UserTokenDTO(
    val user: UserResponseDTO,
    val token: String
)

/**
 * Data transfer object used to serialize a list of [UserResponseDTO] objects
 * @property data list of [UserResponseDTO] objects to be serialized
 * @author BiquesDAM-Team
 */
@Serializable
data class UserDataDTO(
    val data: List<UserResponseDTO>
)
