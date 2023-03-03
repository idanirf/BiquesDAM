package es.dam.biques.microserviciousuarios.repositories

import es.dam.biques.microserviciousuarios.models.User
import es.dam.biques.microserviciousuarios.repositories.cache.UserCachedRepository
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
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
class UsersCachedRepositoryTest {

    private val user = User(
        id = 12L,
        uuid = UUID.fromString("91e0c247-c611-4ed2-8db8-a495f1f16fee"),
        username = "Test",
        email = "test@gmail.com",
        password = "test1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Test Services",
        role = User.TipoUsuario.CLIENT.name
    )

    @MockK
    lateinit var repository: UsersRepository

    @InjectMockKs
    lateinit var repositoryCached: UserCachedRepository


    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(user)
        
        val result = repositoryCached.findAll().toList()
        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(user, result[0]) }
        )

        coVerify(exactly = 1) { repository.findAll() }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { repository.findById(user.id!!) } returns user

        val result = repositoryCached.findById(12L)

        assertAll(
            { assertEquals(user.email, result!!.email) },
            { assertEquals(user.username, result!!.username) },
        )

        coVerify { repository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNoEncontrado() = runTest {
        coEvery { repository.findById(user.id!!) } returns null

        val result = repositoryCached.findById(12L)
        assertNull(result)

        coVerify { repository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByEmail() = runTest {
        coEvery { repository.findUserByEmail(user.email) } returns flowOf(user)

        val result = repositoryCached.findByEmail("test@gmail.com")
        assertAll(
            { assertEquals(user.email, result!!.email) },
            { assertEquals(user.username, result!!.username) },
        )

        coVerify { repository.findUserByEmail(any()) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByEmailNoEncontrado() = runTest {
        coEvery { repository.findUserByEmail(user.email) } returns flowOf()

        val result = repositoryCached.findByEmail("sara@gmail.com")
        assertNull(result)

        coVerify { repository.findUserByEmail(user.email) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByUuid() = runTest {
        coEvery { repository.findUserByUuid(user.uuid) } returns flowOf(user)

        val result = repositoryCached.findByUUID(user.uuid)!!

        assertAll(
            { assertEquals(user.email, result.email) },
            { assertEquals(user.username, result.username) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByUuidNoEncontrado() = runTest {
        coEvery { repository.findUserByUuid(user.uuid) } returns flowOf()

        val result = repositoryCached.findByUUID(user.uuid)

        assertNull(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.save(user) } returns user

        val result = repositoryCached.save(user)

        assertAll(
            { assertEquals(user.email, result.email) },
            { assertEquals(user.username, result.username) },
        )

        coVerify { repository.save(user) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repository.findUserByUuid(user.uuid) } returns flowOf(user)
        coEvery { repository.save(user) } returns user

        val result = repositoryCached.update(user.id!!, user)!!

        assertAll(
            { assertEquals(user.email, result.email) },
            { assertEquals(user.username, result.username) },
        )

        coVerify { repository.save(user) }
        coVerify { repository.findUserByUuid(user.uuid) }
    }
}