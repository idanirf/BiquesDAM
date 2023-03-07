package es.dam.bique.microservicioproductoservicios.services.appointments

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.models.AssistanceType
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class AppointmentServiceTest {

    private val appointment = Appointment(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d12"),
        userId = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d13"),
        assistance = AssistanceType.ANY,
        date = LocalDate.parse("2023-03-03"),
        description = "test"
    )

    @MockK
    lateinit var repository: AppointmentsCachedRepository

    @InjectMockKs
    lateinit var service: AppointmentService

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun findAll() = runTest{
        coEvery { repository.findAll() } returns flowOf(appointment)

        val result = service.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(appointment, result[0]) }
        )

        coVerify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun findById() = runTest{
        coEvery { repository.findById(any()) } returns appointment

        val result = service.findById(appointment.id!!)

        assertAll(
            { assertEquals(appointment.description, result.description) },
            { assertEquals(appointment.date, result.date) },
        )

        coVerify { repository.findById(any()) }
    }

    @Test
    fun findByIdNotFound() = runTest {
        coEvery { repository.findById(any()) } returns null

        val res = assertThrows<AppointmentNotFoundException> {
            service.findById(appointment.id!!)
        }

        assertEquals("Appointment not found with id: ${appointment.id}", res.message)

        coVerify { repository.findById(any()) }
    }

    @Test
    fun findByUuid() = runTest {
        coEvery { repository.findByUuid(any()) } returns appointment

        val result = service.findByUuid(appointment.uuid)

        assertAll(
            { assertEquals(appointment.description, result.description) },
            { assertEquals(appointment.date, result.date) },
        )

        coVerify { repository.findByUuid(any()) }

    }

    @Test
    fun findByUuidNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns null

        val res = assertThrows<AppointmentNotFoundException> {
            service.findByUuid(appointment.uuid)
        }

        assertEquals("Appointment not found with uuid: ${appointment.uuid}", res.message)

        coVerify { repository.findByUuid(any()) }
    }

    @Test
    fun save() = runTest {
        coEvery { repository.findByUuid(any()) } returns appointment
        coEvery { repository.save(any()) } returns appointment

        val result = service.save(appointment)

        assertAll(
            { assertEquals(appointment.description, result.description) },
            { assertEquals(appointment.date, result.date) },
        )

        coVerify { repository.save(any()) }

    }

    @Test
    fun update() = runTest {
        coEvery { repository.findByUuid(any()) } returns appointment
        coEvery { repository.update(any()) } returns appointment

        val result = service.update(appointment)

        assertAll(
            { assertEquals(appointment.description, result.description) },
            { assertEquals(appointment.date, result.date) },
        )

        coVerify {
            repository.update(any())
        }
    }

    @Test
    fun updateNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } throws AppointmentNotFoundException("Appointment not found with uuid: ${appointment.uuid}")
        coEvery { repository.update(any()) } returns appointment

        val res = assertThrows<AppointmentNotFoundException> {
            service.update(appointment)
        }

        assertEquals("Appointment not found with uuid: ${appointment.uuid}", res.message)

        coVerify(exactly = 0) { repository.update(any()) }

    }

    @Test
    fun delete() = runTest {
        coEvery { repository.findByUuid(any()) } returns appointment
        coEvery { repository.delete(any()) } returns false
        coEvery { repository.findByUuid(any()) } returns appointment

        val result = service.delete(appointment)

        assertAll(
            { assertEquals(appointment.description, result.description) },
            { assertEquals(appointment.date, result.date) },
        )

        coVerify { repository.delete(any()) }
    }
}