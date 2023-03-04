package es.dam.bique.microservicioproductoservicios.services.services

import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.exceptions.ServiceNotFoundException
import es.dam.bique.microservicioproductoservicios.models.*
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import es.dam.bique.microservicioproductoservicios.repositories.services.ServiceRepository
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
import org.junit.jupiter.api.assertThrows


@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class ServicesServiceTest {

    private val appointment = Appointment(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d12"),
        userId = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d13"),
        assistance = AssistanceType.ANY,
        date = LocalDate.parse("2023-03-03"),
        description = "test"
    )

    private val entity = Service(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d12"),
        image = "test",
        price = 0.0f,
        appointment = appointment.uuid,
        type = ServiceType.REVISION
    )

    @MockK
    lateinit var repository: ServiceRepository

    @MockK
    lateinit var appointmentRepository: AppointmentsCachedRepository

    @InjectMockKs
    lateinit var service: ServicesService

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(entity)

        val result = service.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(entity, result[0]) }
        )

        coVerify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun findByUuid() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf(entity)

        val result = service.findByUuid(entity.uuid)

        assertAll(
            { assertEquals(entity.image, result.image) },
            { assertEquals(entity.price, result.price) },
        )

        coVerify { repository.findByUuid(any()) }
    }

    @Test
    fun findByUuidNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf()

        val res = assertThrows<ServiceNotFoundException> {
            service.findByUuid(entity.uuid)
        }

        assertEquals("Not found with uuid: ${entity.uuid}", res.message)

        coVerify { repository.findByUuid(any()) }
    }

    @Test
    fun findById() = runTest {
        coEvery { repository.findById(any()) } returns entity

        val result = service.findById(entity.id!!)

        assertAll(
            { assertEquals(entity.image, result.image) },
            { assertEquals(entity.price, result.price) },
        )

        coVerify { repository.findById(any()) }
    }

    @Test
    fun findByIdNotFound() = runTest {
        coEvery { repository.findById(any()) } returns null

        val res = assertThrows<ServiceNotFoundException> {
            service.findById(entity.id!!)
        }

        assertEquals("Not found with id: ${entity.id}", res.message)

        coVerify { repository.findById(any()) }
    }

    @Test
    fun save() = runTest {
        coEvery { appointmentRepository.findByUuid(any()) } returns appointment
        coEvery { repository.findByUuid(any()) } returns flowOf(entity)
        coEvery { repository.save(any()) } returns entity

        val result = service.save(entity)

        assertAll(
            { assertEquals(entity.image, result.image) },
            { assertEquals(entity.price, result.price) },
        )

        coVerify { repository.save(any()) }

    }

    @Test
    fun saveRepresentanteNotExists() = runTest {
        coEvery { appointmentRepository.findByUuid(any()) } throws AppointmentNotFoundException("Appointment not found with uuid: ${appointment.uuid}")
        coEvery { repository.save(any()) } returns entity

        val res = assertThrows<AppointmentNotFoundException> {
            service.save(entity)
        }

        assertEquals("Appointment not found with uuid: ${entity.appointment}", res.message)

    }

    @Test
    fun update() = runTest {
        coEvery { appointmentRepository.findByUuid(any()) } returns appointment
        coEvery { repository.findByUuid(any()) } returns flowOf(entity)
        coEvery { repository.save(any()) } returns entity

        val result = service.update(entity)

        assertAll(
            { assertEquals(entity.image, result.image) },
            { assertEquals(entity.price, result.price) },
        )

        coVerify {
            repository.save(any())
        }
    }

    @Test
    fun updateNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } throws ServiceNotFoundException("Not found with uuid: ${entity.uuid}")
        coEvery { repository.save(any()) } returns entity

        val res = assertThrows<ServiceNotFoundException> {
            service.update(entity)
        }

        assertEquals("Not found with uuid: ${entity.uuid}", res.message)

        coVerify(exactly = 0) { repository.save(any()) }

    }

    @Test
    fun delete() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf(entity)
        coEvery { repository.delete(any()) } returns Unit
        coEvery { repository.findByUuid(any()) } returns flowOf(entity)

        val result = service.delete(entity)

        assertAll(
            { assertEquals(entity.image, result.image) },
            { assertEquals(entity.price, result.price) },
        )

        coVerify { repository.delete(any()) }
    }
}