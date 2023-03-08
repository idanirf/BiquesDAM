package es.dam.biques.microserviciousuarios.mappers

import es.dam.biques.microserviciousuarios.dto.UserRegisterDTO
import es.dam.biques.microserviciousuarios.dto.UserResponseDTO
import es.dam.biques.microserviciousuarios.dto.UserUpdateDTO
import es.dam.biques.microserviciousuarios.models.User

/**
 * Extension function that converts a [User] object into a [UserResponseDTO] object.
 * @return the converted [UserResponseDTO] object
 * @author BiquesDAM-Team
 */
fun User.toDTO(): UserResponseDTO {
    return UserResponseDTO(
        id = id,
        uuid = uuid,
        image = image,
        rol = rol.split(",").map { it.trim() }.toSet(),
        email = email,
        username = username,
        address = address
    )
}

/**
 * Extension function that converts a [User] object into a [UserUpdateDTO] object.
 * @return the converted [UserUpdateDTO] object
 * @author BiquesDAM-Team
 */
fun UserRegisterDTO.toModel(): User {
    return User(
        image = image,
        rol = rol.joinToString(", ") { it.uppercase().trim() },
        email = email,
        username = username,
        password = password,
        address = address
    )
}

/**
 * Extension function that converts a [UserUpdateDTO] object into a [User] object.
 * @return the converted [User] object
 * @author BiquesDAM-Team
 */
fun UserUpdateDTO.toModelFromUpdated(): User {
    return User(
        image = image,
        rol = rol.joinToString(", ") { it.uppercase().trim() },
        email = email,
        username = username,
        password = password,
        address = address
    )
}