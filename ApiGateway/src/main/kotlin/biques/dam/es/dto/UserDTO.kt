package biques.dam.es.dto

import kotlinx.serialization.Serializable


/**
 * This data class represents a DTO for the User model for register.
 * @param image a string representing the image of the user.
 * @param rol a set of strings representing the rol of the user.
 * @param email a string representing the email of the user.
 * @param username a string representing the username of the user.
 * @param password a string representing the password of the user.
 * @param address a string representing the address of the user.
 * @author BiquesDAM-Team
 */
@Serializable
data class UserRegisterDTO(
    val image: String,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

/**
 * Data transfer object for updating an existing user.
 * @param image a string representing the image of the user.
 * @param rol a set of strings representing the rol of the user.
 * @param email a string representing the email of the user.
 * @param username a string representing the username of the user.
 * @param password a string representing the password of the user.
 * @param address a string representing the address of the user.
 * @author BiquesDAM-Team
 */
@Serializable
data class UserUpdateDTO(
    val image: String,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val password: String,
    val address: String
)

/**
 * Data class representing a user login request.
 * @param username a string representing the username of the user.
 * @param password a string representing the password of the user.
 */
@Serializable
data class UserLoginDTO(
    val username: String,
    val password: String
)

/**
 * Data class representing a user response.
 * @param id a long representing the id of the user.
 * @param uuid a string representing the uuid of the user.
 * @param image a string representing the image of the user.
 * @param rol a set of strings representing the rol of the user.
 * @param email a string representing the email of the user.
 * @param username a string representing the username of the user.
 * @param address a string representing the address of the user.
 * @author BiquesDAM-Team
 */
@Serializable
data class UserResponseDTO(
    val id: Long,
    val uuid: String,
    val image: String,
    val rol: Set<String>,
    val email: String,
    val username: String,
    val address: String
)

/**
 * Data class representing a user and its associated token.
 * @param user a user response.
 * @param token a string representing the token of the user.
 */
@Serializable
data class UserTokenDTO(
    val user: UserResponseDTO,
    val token: String
)

/**
 * Data class representing a list of user data for retrieval.
 * @param data a list of user responses.
 */
data class UserDataDTO(
    val data: List<UserResponseDTO>
)