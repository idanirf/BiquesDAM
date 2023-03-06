package biques.dam.es.services.order

import biques.dam.es.models.Order
import biques.dam.es.repositories.order.OrderRepository
import biques.dam.es.services.OrderService
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
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
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.litote.kmongo.id.toId
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class OrderServiceTest {
    private val order = Order(
        ObjectId("223456789912345678901232").toId(),
        UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
        Order.StatusOrder.DELIVERED,
        12.0,
        12.0,
        listOf(
            "ed6f7d0a-7f7a-45bf-8b63-a1aa21383271",
            "e213f434-4c2b-4a28-953f-3981b1ff7e00",
        ),
        UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
    )

    @MockK
    lateinit var orderRepository: OrderRepository

    @InjectMockKs
    lateinit var service: OrderService

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { orderRepository.findAll() } returns flowOf(order)
        val result = service.getAllOrder().toList()
        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(order, result[0]) }
        )
        coVerify(exactly = 1) { orderRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByUuid() = runTest {
        coEvery { orderRepository.findByUUID(order.uuid) } returns order
        val result = service.getOrderByUUID(order.uuid)
        assertAll(
            { assertEquals(order.total, result.total) },
            { assertEquals(order.status, result.status) }
        )
        coVerify { service.getOrderByUUID(order.uuid) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { service.getOrderByUUID(order.uuid) } returns order
        coEvery { service.saveOrder(order) } returns order
        val result = service.saveOrder(order)
        assertAll(
            { assertEquals(order.total, result.total) },
            { assertEquals(order.iva, result.iva) },
        )
        coVerify { service.saveOrder(order) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { service.getOrderByUUID(order.uuid) } returns order
        coEvery { service.updateOrder(order) } returns order

        val result = service.updateOrder(order)

        assertAll(
            { assertEquals(order.total, result.total) },
            { assertEquals(order.iva, result.iva) },
        )
        coVerify {
            orderRepository.update(order)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { service.getOrderByUUID(order.uuid) } returns order
        coEvery { service.deleteOrder(order) } returns true
        coEvery { service.getOrderByUUID(order.uuid) } returns order

        val result = service.deleteOrder(order)

        assertAll(
            { assertTrue(result)}
        )

        coVerify { orderRepository.delete(order) }
    }


}
