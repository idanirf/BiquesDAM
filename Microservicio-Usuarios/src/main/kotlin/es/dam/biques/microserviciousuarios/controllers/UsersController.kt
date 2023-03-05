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
import java.util.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/users")
class UsersController @Autowired constructor(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtils: JWTTokenUtils
) {
    @PostMapping("/login")
    fun login(@RequestBody logingDto: UserLoginDTO): ResponseEntity<UserTokenDTO> {
        try {
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
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/register")
    suspend fun register(@RequestBody usuarioDto: UserRegisterDTO): ResponseEntity<UserTokenDTO> {
        logger.info { "User register: ${usuarioDto.username}" }

        try {
            val user = usuarioDto.validate().toModel()
            user.rol.forEach { println(it) }
            val userInsert = userService.save(user)
            val jwtToken: String = jwtTokenUtils.generateToken(userInsert)

            logger.info { "Token de usuario: $jwtToken" }
            return ResponseEntity.ok(UserTokenDTO(userInsert.toDTO(), jwtToken))
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("")
    suspend fun findAll(@AuthenticationPrincipal user: User): ResponseEntity<List<UserResponseDTO>> {
        logger.info { "API -> findAll()" }

        val res = userService.findAll().toList().map { it.toDTO() }
        return ResponseEntity.ok(res)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<UserResponseDTO> {
        logger.info { "API -> findById($id)" }

        try {
            val res = userService.findUserById(id.toLong()).toDTO()
            return ResponseEntity.ok(res)
        } catch (e: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
//    @GetMapping("/{uuid}")
//    suspend fun findByUUID(@PathVariable uuid: String): ResponseEntity<UserResponseDTO> {
//        logger.info { "API -> findByUUID($uuid)" }
//
//        try {
//            val res = userService.findUserByUuid(uuid).toDTO()
//            return ResponseEntity.ok(res)
//        } catch (e: UserNotFoundException) {
//            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
//        }
//    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/{id}")
    suspend fun update(
        @AuthenticationPrincipal user: User,
        @PathVariable id: String, @RequestBody userDTO: UserUpdateDTO
    ): ResponseEntity<UserResponseDTO> {
        logger.info { "API -> update($id)" }

        try {
            userDTO.validate()
            val updated = user.copy(
                image = userDTO.image,
                address = userDTO.address
            )
            val res = userService.update(id.toLong(), updated)

            return ResponseEntity.status(HttpStatus.OK).body(res?.toDTO())
        } catch (e: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<UserDTO> {
        logger.info { "API -> delete($id)" }

        try {
            userService.deleteById(id.toLong())
            return ResponseEntity.noContent().build()
        } catch (e: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: UserBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}