package es.dam.biques.microserviciousuarios.mappers

import es.dam.biques.microserviciousuarios.dto.UserCreateDTO
import es.dam.biques.microserviciousuarios.dto.UserDTO
import es.dam.biques.microserviciousuarios.models.User

fun User.toDTO(): UserDTO {
    return UserDTO(
        id = id,
        uuid = uuid.toString(),
        image = image,
        type = type.split(",").map { it.trim() }.toSet(),
        email = email,
        username = username,
        password = password,
        address = address,
        metadata = UserDTO.Metadata(
            createdAt = createdAt,
            updatedAt = updatedAt,
            deleted = deleted
        )
    )
}

fun UserCreateDTO.toModel(): User {
    return User(
        image = image,
        type = type.joinToString(", ") { it.uppercase().trim() },
        email = email,
        username = username,
        password = password,
        address = address
    )
}