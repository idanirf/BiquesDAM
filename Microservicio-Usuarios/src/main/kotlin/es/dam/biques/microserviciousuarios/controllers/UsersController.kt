package es.dam.biques.microserviciousuarios.controllers

import es.dam.biques.microserviciousuarios.config.security.jwt.JWTTokenUtils
import es.dam.biques.microserviciousuarios.dto.UserLoginDTO
import es.dam.biques.microserviciousuarios.dto.UserTokenDTO
import es.dam.biques.microserviciousuarios.mappers.toDTO
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.service.UserService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/users")
class UsersController @Autowired constructor(
    private val userService: UserService,
    // TODO: Hacer el bean personalizado para que funcione el autowired.
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtils: JWTTokenUtils
    // TODO: ¿servicio de storage?
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody logingDto: UserLoginDTO): ResponseEntity<UserTokenDTO> {
        logger.info { "User login: ${logingDto.username}" }

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                logingDto.username,
                logingDto.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as User

        val jwtToken: String = jwtTokenUtils.generateToken(user)
        logger.info { "Token de usuario: $jwtToken" }

        val userWithToken = UserTokenDTO(user.toDTO(), jwtToken)

        return ResponseEntity.ok(userWithToken)
    }
}