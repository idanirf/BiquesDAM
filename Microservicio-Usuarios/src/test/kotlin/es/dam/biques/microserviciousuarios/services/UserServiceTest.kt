package es.dam.biques.microserviciousuarios.services


import es.dam.biques.microserviciousuarios.exceptions.UserBadRequestException
import es.dam.biques.microserviciousuarios.exceptions.UserNotFoundException
import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.repositories.UsersRepository
import es.dam.biques.microserviciousuarios.repositories.cache.UserCachedRepository
import es.dam.biques.microserviciousuarios.service.UserService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
class UserServiceTest {

    private val user = User(
        id = 12L,
        uuid = "91e0c247-c611-4ed2-8db8-a495f1f16fee",
        username = "Test",
        email = "test@gmail.com",
        password = "test1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Test Services",
        rol = User.TipoUsuario.ADMIN.name
    )

    @MockK
    lateinit var repository: UsersRepository

    @MockK
    lateinit var repositoryCached: UserCachedRepository

    @MockK
    lateinit var passwordEncoder: PasswordEncoder

    @InjectMockKs
    lateinit var userService: UserService


    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadByUsername() = runTest {
        coEvery { repository.findUserByUsername(any()) } returns flowOf(user)

        val result = userService.loadUserByUsername(user.username)

        assertAll(
            { assertEquals(user.username, result.username) },
        )

        coVerify { repository.findUserByUsername(any()) }
    }


    @Test
    fun loadByUsernameNoEncontrado() = runTest {
        coEvery { repository.findUserByUsername(any()) } returns flowOf(user)

        val result = userService.loadUserByUsername(user.username)

        assertAll(
            { assertEquals(user.username, result.username) },
        )

        coVerify { repository.findUserByUsername(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { repositoryCached.findAll() } returns flowOf(user)

        val result = userService.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(user, result[0]) }
        )

        coVerify(exactly = 1) { repositoryCached.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findUserById() = runTest {
        coEvery { repositoryCached.findById(any()) } returns user

        val result = userService.findUserById(user.id!!)

        assertAll(
            { assertEquals(user.username, result.username) },
            { assertEquals(user.email, result.email) },
        )

        coVerify { repositoryCached.findById(any()) }
    }


    @Test
    fun findUserByIdNoEncontrado() = runTest {
        coEvery { repositoryCached.findById(any()) } returns null

        val res = assertThrows<UserNotFoundException> {
            userService.findUserById(user.id!!)
        }

        assertEquals("User with id ${user.id} not found.", res.message)

        coVerify { repositoryCached.findById(any()) }

    }

    /*
        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun findUserByUUID() = runTest {
            coEvery { repositoryCached.findByUUID(any()) } returns user

            val result = userService.findUserByUuid(user.uuid)

            assertAll(
                { assertEquals(user.username, result.username) },
                { assertEquals(user.email, result.email) },
            )

            coVerify { repositoryCached.findByUUID(any()) }
        }


        @Test
        fun findUserByUUIDNoEncontrado() = runTest {
            coEvery { repositoryCached.findByUUID(any()) } returns null

            val res = assertThrows<UserNotFoundException> {
                userService.findUserByUuid(user.uuid)
            }

            assertEquals("User with uuid ${user.uuid} not found.", res.message)

            coVerify { repositoryCached.findByUUID(any()) }

        }


     */

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.findUserByUsername(any()) } returns flowOf()
        coEvery { repository.findUserByEmail(any()) } returns flowOf()
        coEvery { passwordEncoder.encode(any()) } returns "encoded_password"
        coEvery { repositoryCached.save(any()) } returns user

        val result = userService.save(user, true)

        assertAll(
            { assertEquals(user.username, result.username) },
            { assertEquals(user.email, result.email) },
        )

        coVerify(exactly = 1) { repository.findUserByUsername(any()) }
        coVerify(exactly = 1) { repository.findUserByEmail(any()) }
        coVerify(exactly = 1) { passwordEncoder.encode(any()) }
        coVerify(exactly = 1) { repositoryCached.save(any()) }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveNoEncontradoUsername() = runTest {
        coEvery { repository.findUserByUsername(any()) } returns flowOf(user)

        assertThrows<UserBadRequestException> { userService.save(user) }

        coVerify(exactly = 1) { repository.findUserByUsername(any()) }
        coVerify(exactly = 0) { repository.findUserByEmail(any()) }
        coVerify(exactly = 0) { passwordEncoder.encode(any()) }
        coVerify(exactly = 0) { repositoryCached.save(any()) }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveNoEncontradoEmail() = runTest {
        coEvery { repository.findUserByUsername(any()) } returns flowOf()
        coEvery { repository.findUserByEmail(any()) } returns flowOf(user)

        assertThrows<UserBadRequestException> { userService.save(user) }

        coVerify(exactly = 1) { repository.findUserByUsername(any()) }
        coVerify(exactly = 1) { repository.findUserByEmail(any()) }
        coVerify(exactly = 0) { passwordEncoder.encode(any()) }
        coVerify(exactly = 0) { repositoryCached.save(any()) }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repositoryCached.findByUUID(any()) } returns user
        coEvery { repositoryCached.update(any(), any()) } returns user

        val result = userService.update(user.id!!, user)

        assertAll(
            { assertEquals(user.username, result?.username) },
            { assertEquals(user.email, result?.email) },
        )

        coVerify { repositoryCached.update(any(), any()) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateNoEncontrado() = runTest {
        coEvery {
            repositoryCached.update(
                any(),
                any()
            )
        } throws UserNotFoundException("User with id ${user.id} not found")

        val result = assertThrows<UserNotFoundException> {
            userService.update(user.id!!, user)
        }

        assertEquals("User with id ${user.id} not found.", result.message)

        coVerify(exactly = 1) { repositoryCached.update(any(), any()) }


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteById() = runTest {
        coEvery { repositoryCached.findByUUID(any()) } returns user
        coEvery { repositoryCached.deleteById(any()) } returns user

        val result = userService.deleteById(user.id!!)

        assertAll(
            { assertEquals(user.username, result?.username) },
            { assertEquals(user.email, result?.email) },
        )

        coVerify { repositoryCached.deleteById(any()) }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteIdNoEncontrado() = runTest {
        coEvery { repositoryCached.deleteById(any()) } throws UserNotFoundException("User with id ${user.id} not found.")

        val res = assertThrows<UserNotFoundException> {
            userService.deleteById(user.id!!)
        }

        assertEquals("User with id ${user.id} not found.", res.message)

        coVerify(exactly = 1) { repositoryCached.deleteById(any()) }

    }


}