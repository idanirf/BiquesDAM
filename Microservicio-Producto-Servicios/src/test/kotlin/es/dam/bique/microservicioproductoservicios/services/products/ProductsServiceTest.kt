package es.dam.bique.microservicioproductoservicios.services.products

import es.dam.bique.microservicioproductoservicios.exceptions.ProductNotFoundException
import es.dam.bique.microservicioproductoservicios.models.Product
import es.dam.bique.microservicioproductoservicios.models.ProductType
import es.dam.bique.microservicioproductoservicios.models.StockType
import es.dam.bique.microservicioproductoservicios.repositories.products.ProductsCachedRepository
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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class ProductsServiceTest {

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
    lateinit var repository: ProductsCachedRepository

    @InjectMockKs
    lateinit var service: ProductsService

    init {
        MockKAnnotations.init(this)
    }


    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(product)

        val result = service.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(product, result[0]) }
        )

        coVerify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun findByUuid() = runTest {
        coEvery { repository.findByUuid(any()) } returns product

        val result = service.findByUuid(product.uuid)

        assertAll(
            { assertEquals(product.description, result.description) },
            { assertEquals(product.brand, result.brand) },
        )

        coVerify { repository.findByUuid(any()) }
    }

    @Test
    fun findByUuidNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } returns null

        val res = assertThrows<ProductNotFoundException> {
            service.findByUuid(product.uuid)
        }

        assertEquals("Not found with uuid: ${product.uuid}", res.message)

        coVerify { repository.findByUuid(any()) }
    }

    @Test
    fun findById() = runTest{
        coEvery { repository.findById(any()) } returns product

        val result = service.findById(product.id!!)

        assertAll(
            { assertEquals(product.description, result.description) },
            { assertEquals(product.brand, result.brand) },
        )

        coVerify { repository.findById(any()) }
    }

    @Test
    fun findByIdNotFound() = runTest {
        coEvery { repository.findById(any()) } returns null

        val res = assertThrows<ProductNotFoundException> {
            service.findById(product.id!!)
        }

        assertEquals("Not found with id: ${product.id}", res.message)

        coVerify { repository.findById(any()) }
    }

    @Test
    fun save() = runTest {
        coEvery { repository.findByUuid(any()) } returns product
        coEvery { repository.save(any()) } returns product

        val result = service.save(product)

        assertAll(
            { assertEquals(product.description, result.description) },
            { assertEquals(product.brand, result.brand) },
        )

        coVerify { repository.save(any()) }

    }

    @Test
    fun update() = runTest {
        coEvery { repository.findByUuid(any()) } returns product
        coEvery { repository.update(any()) } returns product

        val result = service.update(product)

        assertAll(
            { assertEquals(product.description, result.description) },
            { assertEquals(product.brand, result.brand) },
        )

        coVerify {
            repository.update(any())
        }
    }

    @Test
    fun updateNotFound() = runTest {
        coEvery { repository.findByUuid(any()) } throws ProductNotFoundException("Not found with uuid: ${product.uuid}")
        coEvery { repository.update(any()) } returns product

        val res = assertThrows<ProductNotFoundException> {
            service.update(product)
        }

        assertEquals("Not found with uuid: ${product.uuid}", res.message)

        coVerify(exactly = 0) { repository.update(any()) }

    }

    @Test
    fun delete() = runTest {
        coEvery { repository.findByUuid(any()) } returns product
        coEvery { repository.delete(any()) } returns false
        coEvery { repository.findByUuid(any()) } returns product

        val result = service.delete(product)

        assertAll(
            { assertEquals(product.description, result.description) },
            { assertEquals(product.brand, result.brand) },
        )

        coVerify { repository.delete(any()) }
    }
}