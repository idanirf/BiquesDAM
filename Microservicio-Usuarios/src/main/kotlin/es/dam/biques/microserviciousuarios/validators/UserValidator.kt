package es.dam.biques.microserviciousuarios.validators

import es.dam.biques.microserviciousuarios.dto.UserCreateDTO
import es.dam.biques.microserviciousuarios.dto.UserUpdateDTO
import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException


fun UserCreateDTO.validate(): UserCreateDTO {
    if (this.username.isBlank()) {
        throw UserBadRequestException("The username cannot be empty")
    } else if (this.email.isBlank() || !this.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$")))
        throw UserBadRequestException("The email cannot be empty or does not have the correct format")
    else if (this.password.isBlank() || this.password.length < 5)
        throw UserBadRequestException("The password cannot be empty or less than 5 characters")
    else if (this.address.isBlank())
        throw UserBadRequestException("The address cannot be empty")
    else if (this.type.isEmpty())
        throw UserBadRequestException("The type cannot be empty")

    return this
}


fun UserUpdateDTO.validate(): UserUpdateDTO {
    if (this.username.isBlank()) {
        throw UserBadRequestException("The username cannot be empty")
    } else if (this.email.isBlank() || !this.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$")))
        throw UserBadRequestException("The email cannot be empty or does not have the correct format")
    else if (this.password.isBlank() || this.password.length < 5)
        throw UserBadRequestException("The password cannot be empty or less than 5 characters")
    else if (this.address.isBlank())
        throw UserBadRequestException("The address cannot be empty")
    else if (this.type.isEmpty())
        throw UserBadRequestException("The type cannot be empty")

    return this
}