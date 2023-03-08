package biques.dam.es.services.orderLine

import biques.dam.es.models.OrderLine
import biques.dam.es.repositories.order.OrderRepository
import biques.dam.es.repositories.orderline.OrderLineRepository
import biques.dam.es.services.OrderLineService
import biques.dam.es.services.OrderService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.id.toId
import java.util.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class OrderLineServiceTest {
    private val orderLine = OrderLine(
        ObjectId("123456789912345678901232").toId(),
        UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
        UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
        3,
        5.0,
        15.0,
        1
    )
    @MockK
    lateinit var orderLineRepository: OrderLineRepository
    @InjectMockKs
    lateinit var service: OrderLineService

    @Test
    fun findAll() = runTest {
        coEvery { orderLineRepository.findAll() } returns flowOf(orderLine)
        val result = service.getAllOrderLine().toList()
        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(orderLine, result[0]) }
        )
        coVerify(exactly = 1) { orderLineRepository.findAll() }
    }

    @Test
    fun findByUuid() = runTest {
        coEvery { orderLineRepository.findByUUID(any()) } returns orderLine
        val result = service.getOrderLineByUUID(orderLine.uuid)
        assertAll(
            { assertEquals(orderLine.total, result.total) },
            { assertEquals(orderLine.amount, result.amount) }
        )
        coVerify { service.getOrderLineByUUID(any()) }
    }

    @Test
    fun save() = runTest {
        coEvery { service.getOrderLineByUUID(any()) } returns orderLine
        coEvery { service.saveOrderLine(any()) } returns orderLine
        val result = service.saveOrderLine(orderLine)
        assertAll(
            { assertEquals(orderLine.total, result.total) },
            { assertEquals(orderLine.amount, result.amount) },
        )
        coVerify { service.saveOrderLine(any()) }
    }

    @Test
    fun update() = runTest {
        coEvery { service.getOrderLineByUUID(any()) } returns orderLine
        coEvery { service.updateOrderLine(any()) } returns orderLine

        val result = service.updateOrderLine(orderLine)

        assertAll(
            { assertEquals(orderLine.total, result.total) },
            { assertEquals(orderLine.amount, result.amount) },
        )

        coVerify {
            orderLineRepository.update(any())
        }
    }

    /*
    @Test
    fun delete() = runTest {
        coEvery { service.getOrderByUUID(any()) } returns order
        coEvery { service.deleteOrder(any()) } returns false
        coEvery { service.getOrderByUUID(any()) } returns order

        val result = service.deleteOrder(order)

        assertAll(
            { assertEquals(order.total, result.total) },
            { assertEquals(order.iva, result.iva) }
        )

        coVerify { orderRepository.delete(any()) }
    }

     */
}