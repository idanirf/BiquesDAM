package es.dam.biques.microserviciousuarios.mappers

import es.dam.biques.microserviciousuarios.dto.UserRegisterDTO
import es.dam.biques.microserviciousuarios.dto.UserResponseDTO
import es.dam.biques.microserviciousuarios.dto.UserUpdateDTO
import es.dam.biques.microserviciousuarios.models.User

fun User.toDTO(): UserResponseDTO {
    return UserResponseDTO(
        id = id,
        uuid = uuid.toString(),
        image = image,
        rol = role.split(",").map { it.trim() }.toSet(),
        email = email,
        username = username,
        address = address
    )
}

fun UserRegisterDTO.toModel(): User {
    return User(
        image = image,
        role = rol.joinToString(", ") { it.uppercase().trim() },
        email = email,
        username = username,
        password = password,
        address = address
    )
}

fun UserUpdateDTO.toModelFromUpdated(): User {
    return User(
        image = image,
        role = rol.joinToString(", ") { it.uppercase().trim() },
        email = email,
        username = username,
        password = password,
        address = address
    )
}