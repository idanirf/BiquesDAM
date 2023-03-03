package es.dam.biques.microserviciousuarios.services

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
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
class UserServiceTest {

    private val user = User(
        id = 12L,
        uuid = UUID.fromString("91e0c247-c611-4ed2-8db8-a495f1f16fee"),
        username = "TestService",
        email = "testService@gmail.com",
        password = "test1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Test Services",
        role = User.TipoUsuario.ADMIN.name
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



}