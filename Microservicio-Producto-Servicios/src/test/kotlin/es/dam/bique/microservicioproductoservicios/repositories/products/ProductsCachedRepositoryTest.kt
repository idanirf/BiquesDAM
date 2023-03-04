package es.dam.bique.microservicioproductoservicios.repositories.products

import es.dam.bique.microservicioproductoservicios.models.*
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsCachedRepository
import es.dam.bique.microservicioproductoservicios.repositories.appointments.AppointmentsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class ProductsCachedRepositoryTest {

    private val product = Product(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d12"),
        image = "test",
        brand = "test",
        model = "test",
        description = "test",
        price = 0.0f,
        discountPercentage = 0.0f,
        stock = StockType.SOLDOUT,
        isAvailable = false,
        type = ProductType.ACCESORIES
    )

    @MockK
    lateinit var repository: ProductsRepository

    @InjectMockKs
    lateinit var cachedRepository: ProductsCachedRepository


    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(product)

        val result = cachedRepository.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(product, result[0]) }
        )

        coVerify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun findById() = runTest {
        coEvery { repository.findById(any()) } returns product

        val result = cachedRepository.findById(1L)

        assertAll(
            { assertEquals(product.model, result!!.model) },
            { assertEquals(product.brand, result!!.brand) },
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
        coEvery { repository.findByUuid(any()) } returns flowOf(product)

        val result = cachedRepository.findByUuid(product.uuid)

        assertAll(
            { assertEquals(product.model, result?.model) },
            { assertEquals(product.description, result?.description) },
        )
    }

    @Test
    fun findByUuidNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf()

        val result = cachedRepository.findByUuid(product.uuid)

        assertNull(result)
    }

    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns product

        val result = cachedRepository.save(product)

        assertAll(
            { assertEquals(product.model, result.model) },
            { assertEquals(product.description, result.description) },
        )

        coVerify { repository.save(any()) }
    }

    @Test
    fun update() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf(product)
        coEvery { repository.save(any()) } returns product

        val result = cachedRepository.save(product)

        assertAll(
            { assertEquals(product.model, result.model) },
            { assertEquals(product.description, result.description) },
        )

        coVerify { repository.save(any()) }
    }

    @Test
    fun updateNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns flowOf()

        val result = cachedRepository.update(product)

        assertNull(result)

    }

    @Test
    fun delete() = runTest {
        coEvery { repository.findById(any()) } returns product
        coEvery { repository.deleteById(any()) } returns Unit

        cachedRepository.delete(product)

    }
}