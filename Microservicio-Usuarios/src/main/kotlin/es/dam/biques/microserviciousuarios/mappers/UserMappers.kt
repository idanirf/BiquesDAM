package es.dam.biques.microserviciousuarios.mappers

import es.dam.biques.microserviciousuarios.dto.UsuarioCreateDTO
import es.dam.biques.microserviciousuarios.dto.UsuarioDTO
import es.dam.biques.microserviciousuarios.models.Usuario

fun Usuario.toDTO(): UsuarioDTO {
    return UsuarioDTO(
        id = id,
        uuid = uuid,
        imagen = imagen,
        tipo = tipo.split(",").map { it.trim() }.toSet(),
        email = email,
        username = username,
        password = password,
        direccion = direccion,
        metadata = UsuarioDTO.Metadata(
            createdAt = createdAt,
            updatedAt = updatedAt,
            deleted = deleted
        )
    )
}

fun UsuarioCreateDTO.toModel(): Usuario {
    return Usuario(
        imagen = imagen,
        tipo = tipo.joinToString(", ") { it.uppercase().trim() },
        email = email,
        username = username,
        password = password,
        direccion = direccion
    )
}