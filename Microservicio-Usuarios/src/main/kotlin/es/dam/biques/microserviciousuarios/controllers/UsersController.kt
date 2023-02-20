package es.dam.biques.microserviciousuarios.controllers

import es.dam.biques.microserviciousuarios.config.security.jwt.JWTTokenUtils
import es.dam.biques.microserviciousuarios.dto.*
import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException
import es.dam.biques.microserviciousuarios.mappers.toDTO
import es.dam.biques.microserviciousuarios.mappers.toModel
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.service.UserService
import es.dam.biques.microserviciousuarios.validators.validate
import jakarta.validation.Valid
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/")
class UsersController @Autowired constructor(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtils: JWTTokenUtils
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


    @PostMapping("/register")
    suspend fun register(@Valid @RequestBody usuarioDto: UserCreateDTO): ResponseEntity<UserTokenDTO> {
        logger.info { "User register: ${usuarioDto.username}" }

        try {

            val user = usuarioDto.validate().toModel()
            user.type.forEach { println(it) }
            val userInsert = userService.save(user)
            val jwtToken: String = jwtTokenUtils.generateToken(userInsert)
            logger.info { "Token de usuario: ${jwtToken}" }
            return ResponseEntity.ok(UserTokenDTO(userInsert.toDTO(), jwtToken))


        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    // TODO: Revisar este endpoint o en el microservicio A
    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('SUPERADMIN')")
    @GetMapping("/list")
    suspend fun list(@AuthenticationPrincipal user: User): ResponseEntity<List<UserDTO>> {

        logger.info { "Getting list of users" }
        val res = userService.findAll().toList().map { it.toDTO() }
        return ResponseEntity.ok(res)
    }


}