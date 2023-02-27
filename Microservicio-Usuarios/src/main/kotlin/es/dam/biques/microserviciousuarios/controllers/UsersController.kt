package es.dam.biques.microserviciousuarios.controllers

import es.dam.biques.microserviciousuarios.config.security.jwt.JWTTokenUtils
import es.dam.biques.microserviciousuarios.dto.*
import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException
import es.dam.biques.microserviciousuarios.exceptions.UserNotFoundException
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
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("")
class UsersController @Autowired constructor(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtils: JWTTokenUtils
) {
    @GetMapping("/login")
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

        return ResponseEntity.ok(UserTokenDTO(user.toDTO(), jwtToken))
    }


    @PostMapping("/register")
    suspend fun register(@Valid @RequestBody usuarioDto: UserCreateDTO): ResponseEntity<UserTokenDTO> {
        logger.info { "User register: ${usuarioDto.username}" }

        try {
            val user = usuarioDto.validate().toModel()
            user.role.forEach { println(it) }
            val userInsert = userService.save(user)
            val jwtToken: String = jwtTokenUtils.generateToken(userInsert)

            logger.info { "Token de usuario: $jwtToken" }
            return ResponseEntity.ok(UserTokenDTO(userInsert.toDTO(), jwtToken))
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('SUPERADMIN')")
    @GetMapping("/users")
    suspend fun findAll(@AuthenticationPrincipal user: User): ResponseEntity<List<UserDTO>> {
        logger.info { "API -> findAll()" }

        val res = userService.findAll().toList().map { it.toDTO() }
        return ResponseEntity.ok(res)
    }

    //    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('SUPERADMIN')")
    @GetMapping("/users/{id}")
    suspend fun findById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        logger.info { "API -> findById($id)" }

        try {
            val res = userService.findUserById(id)?.toDTO()
            return ResponseEntity.ok(res)
        } catch (e: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('SUPERADMIN')")
    @PostMapping("/users")
    suspend fun create(@Valid @RequestBody userDTO: UserCreateDTO): ResponseEntity<UserDTO> {
        logger.info { "API -> create($userDTO)" }

        try {
            val rep = userDTO.validate().toModel()
            val res = userService.save(rep).toDTO()

            return ResponseEntity.status(HttpStatus.CREATED).body(res)
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('SUPERADMIN')")
    @PutMapping("/users/{id}")
    suspend fun update(
        @PathVariable id: Long, @Valid @RequestBody userDTO: UserCreateDTO
    ): ResponseEntity<UserDTO> {
        logger.info { "API -> update($id)" }

        try {
            val rep = userDTO.validate().toModel()
            val res = userService.update(id, rep)?.toDTO()
            return ResponseEntity.status(HttpStatus.OK).body(res)
        } catch (e: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('SUPERADMIN')")
    @DeleteMapping("/users/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<UserDTO> {
        logger.info { "API -> delete($id)" }

        try {
            userService.deleteById(id)
            return ResponseEntity.noContent().build()
        } catch (e: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}