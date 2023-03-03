package es.dam.biques.microserviciousuarios.controllers

import es.dam.biques.microserviciousuarios.config.security.jwt.JWTTokenUtils
import es.dam.biques.microserviciousuarios.dto.UserRegisterDTO
import es.dam.biques.microserviciousuarios.dto.UserUpdateDTO
import es.dam.biques.microserviciousuarios.exceptions.UserNotFoundException
import es.dam.biques.microserviciousuarios.mappers.toDTO
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.service.UserService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.server.ResponseStatusException
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
class UsersControllerTest {
    @MockK
    private lateinit var userService: UserService

    @MockK
    private lateinit var authenticationManager: AuthenticationManager

    @MockK
    private lateinit var jwtUtils: JWTTokenUtils

    @InjectMockKs
    lateinit var usersController: UsersController

    final val user = User(
        id = 12L,
        uuid = UUID.fromString("91e0c247-c611-4ed2-8db8-a495f1f16fee"),
        username = "TestService",
        email = "testService@gmail.com",
        password = "test1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Test Services",
        rol = User.TipoUsuario.ADMIN.name
    )

    val userResponseDTO = user.toDTO()

    val userRegisterDTO = UserRegisterDTO(
        username = "TestService",
        email = "testService@gmail.com",
        password = "test1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Test Services",
        rol = setOf(User.TipoUsuario.ADMIN.name)
    )

    val userUpdateDTO = UserUpdateDTO(
        username = "TestService",
        email = "testService@gmail.com",
        password = "test1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Test Services",
        rol = setOf(User.TipoUsuario.ADMIN.name)
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { userService.findAll() } returns flowOf(user)

        val result = usersController.findAll(user)
        val dtoBody = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(dtoBody) },
            { assertEquals(1, dtoBody.size) },
            { assertEquals(userResponseDTO.id, dtoBody[0].id) },
            { assertEquals(userResponseDTO.username, dtoBody[0].username) },
            { assertEquals(userResponseDTO.email, dtoBody[0].email) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
        )

        coVerify(exactly = 1) { userService.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { userService.findUserById(user.id!!) } returns user

        val result = usersController.findById(user.id.toString())
        val responseDTO = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(responseDTO) },
            { assertEquals(userResponseDTO.id, responseDTO.id) },
            { assertEquals(userResponseDTO.username, responseDTO.username) },
            { assertEquals(userResponseDTO.email, responseDTO.email) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
        )

        coVerify(exactly = 1) { userService.findUserById(userResponseDTO.id!!) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotFound() = runTest {
        coEvery { userService.findUserById(user.id!!) } throws UserNotFoundException("User with id ${user.id} not found.")

        val exception = assertThrows<ResponseStatusException> {
            usersController.findById(user.id.toString())
        }

        assertEquals("""404 NOT_FOUND "User with id ${user.id} not found."""", exception.message)

        coVerify(exactly = 1) { userService.findUserById(user.id!!) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun create() = runTest {
        coEvery { userService.save(any()) } returns user

        val result = usersController.create(userRegisterDTO)
        val responseDTO = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(responseDTO) },
            { assertEquals(userResponseDTO.id, responseDTO.id) },
            { assertEquals(userResponseDTO.username, responseDTO.username) },
            { assertEquals(userResponseDTO.email, responseDTO.email) },
            { assertEquals(result.statusCode, HttpStatus.CREATED) },
        )

        coVerify(exactly = 1) { userService.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { userService.update(any(), any()) } returns user

        val result = usersController.update(user, user.id.toString(), userUpdateDTO)
        val responseDTO = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(responseDTO) },
            { assertEquals(userResponseDTO.id, responseDTO.id) },
            { assertEquals(userResponseDTO.username, responseDTO.username) },
            { assertEquals(userResponseDTO.email, responseDTO.email) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
        )

        coVerify(exactly = 1) { userService.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateNotFound() = runTest {
        coEvery { userService.update(any(), any()) } throws UserNotFoundException("User with id ${user.id} not found.")

        val exception = assertThrows<ResponseStatusException> {
            usersController.update(user, user.id.toString(), userUpdateDTO)
        }

        assertEquals("""404 NOT_FOUND "User with id ${user.id} not found."""", exception.message)

        coVerify(exactly = 1) { userService.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { userService.deleteById(user.id!!) } returns user

        val result = usersController.delete(user.id.toString())

        assertAll(
            { assertNotNull(result) },
            { assertEquals(result.statusCode, HttpStatus.NO_CONTENT) },
        )

        coVerify(exactly = 1) { userService.deleteById(user.id!!) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNotFound() = runTest {
        coEvery { userService.deleteById(user.id!!) } throws UserNotFoundException("User with id ${user.id} not found.")

        val exception = assertThrows<ResponseStatusException> {
            usersController.delete(user.id.toString())
        }

        assertEquals("""404 NOT_FOUND "User with id ${user.id} not found."""", exception.message)
    }
}