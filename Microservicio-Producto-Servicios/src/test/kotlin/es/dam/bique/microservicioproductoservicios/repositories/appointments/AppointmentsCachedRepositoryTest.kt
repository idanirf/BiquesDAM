package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.models.AssistanceType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class AppointmentsCachedRepositoryTest{

    private val appointment = Appointment(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d12"),
        userId = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d13"),
        assistance = AssistanceType.ANY,
        date = LocalDate.parse("2023-03-03"),
        description = "test"
    )

    @MockK
    lateinit var repository: AppointmentsRepository

    @InjectMockKs
    lateinit var cachedRepository: AppointmentsCachedRepository


    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(appointment)

        val result = cachedRepository.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(appointment, result[0]) }
        )

        coVerify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun findById() = runTest {
        coEvery { repository.findById(any()) } returns appointment

        val result = cachedRepository.findById(1L)

        assertAll(
            { assertEquals(appointment.assistance, result!!.assistance) },
            { assertEquals(appointment.description, result!!.description) },
        )

        coVerify { repository.findById(any()) }
    }

    @Test
    fun findByIdNotFound() = runTest {
        coEvery { repository.findById(any()) } returns null

        val result = cachedRepository.findById(1L)

        assertNull(result)

        coVerify { repository.findById(any()) }
    }

    @Test
    fun findByUuid() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf(appointment)

        val result = cachedRepository.findByUuid(appointment.uuid)

        assertAll(
            { assertEquals(appointment.date, result?.date) },
            { assertEquals(appointment.description, result?.description) },
        )
    }

    @Test
    fun findByUuidNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf()

        val result = cachedRepository.findByUuid(appointment.uuid)

        assertNull(result)
    }

    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns appointment

        val result = cachedRepository.save(appointment)

        assertAll(
            { assertEquals(appointment.date, result.date) },
            { assertEquals(appointment.description, result.description) },
        )

        coVerify { repository.save(any()) }
    }

    @Test
    fun update() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf(appointment)
        coEvery { repository.save(any()) } returns appointment

        val result = cachedRepository.save(appointment)

        assertAll(
            { assertEquals(appointment.date, result.date) },
            { assertEquals(appointment.description, result.description) },
        )

        coVerify { repository.save(any()) }
    }

    @Test
    fun updateNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf()

        val result = cachedRepository.update(appointment)

        assertNull(result)

    }

    @Test
    fun delete() = runTest {
        coEvery { repository.findById(any()) } returns appointment
        coEvery { repository.deleteById(any()) } returns Unit

        cachedRepository.delete(appointment)

    }
}