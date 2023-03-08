package es.dam.biques.microserviciousuarios.validators

import es.dam.biques.microserviciousuarios.dto.UserRegisterDTO
import es.dam.biques.microserviciousuarios.dto.UserUpdateDTO
import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException

/**
 * Validates the properties of the UserRegisterDTO object and throws an exception if any of the validations fail.
 * @throws UserBadRequestException if any of the validations fail.
 * @return The validated UserRegisterDTO object.
 * @author BiquesDAM-Team
 */
fun UserRegisterDTO.validate(): UserRegisterDTO {
    if (this.username.isNotBlank()) {
        if (this.email.isNotBlank() && this.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))) {
            if (this.password.isBlank() || this.password.length < 5)
                throw UserBadRequestException("The password cannot be empty or less than 5 characters")
            else if (this.address.isBlank())
                throw UserBadRequestException("The address cannot be empty")
            else if (this.rol.isEmpty())
                throw UserBadRequestException("The rol cannot be empty")
        } else
            throw UserBadRequestException("The email cannot be empty or does not have the correct format")
    } else {
        throw UserBadRequestException("The username cannot be empty")
    }
    return this
}

/**
 * Validates the properties of the UserUpdateDTO object and throws an exception if any of the validations fail.
 * @throws UserBadRequestException if any of the validations fail.
 * @return The validated UserUpdateDTO object.
 * @author BiquesDAM-Team
 */
fun UserUpdateDTO.validate(): UserUpdateDTO {
    if (this.username.isBlank()) {
        throw UserBadRequestException("The username cannot be empty")
    } else if (this.email.isBlank() || !this.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$")))
        throw UserBadRequestException("The email cannot be empty or does not have the correct format")
    else if (this.password.isBlank() || this.password.length < 5)
        throw UserBadRequestException("The password cannot be empty or less than 5 characters")
    else if (this.address.isBlank())
        throw UserBadRequestException("The address cannot be empty")
    else if (this.rol.isEmpty())
        throw UserBadRequestException("The rol cannot be empty")
    return this
}