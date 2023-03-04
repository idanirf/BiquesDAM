package biques.dam.es.repositories.order


import biques.dam.es.db.MongoDbManager
import biques.dam.es.models.Order
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.litote.kmongo.id.toId
import org.litote.kmongo.reactivestreams.deleteOneById
import org.litote.kmongo.reactivestreams.getCollection
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OrderRepositoryImplTest {
    private val db = MongoDbManager.database.database
    private val orderRepository = OrderRepositoryImpl()
    private val order = Order(
        ObjectId("223456789912345678901232").toId(),
        UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
        Order.StatusOrder.DELIVERED,
        12.0,
        12.0,
        ObjectId("123456789912345678901232").toId(),
        UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
    )


    @BeforeEach
    fun setUp(): Unit = runBlocking {
        db.getCollection<Order>().deleteOneById(order.id)
        orderRepository.save(order)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByUUID() = runTest {
        db.getCollection<Order>().deleteOneById(order.id)
        val orderTest = orderRepository.findByUUID(order.uuid)
        assertAll(
            { assertEquals(order.id, orderTest.id)},
            { assertEquals(order.uuid, orderTest.uuid)},
            { assertEquals(order.status, orderTest.status) },
            { assertEquals(order.total, orderTest.total) },
            { assertEquals(order.iva, orderTest.iva)},
            { assertEquals(order.orderLine, orderTest.orderLine)},
            { assertEquals(order.cliente, orderTest.cliente)}
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest{
        orderRepository.save(order)
        val all = orderRepository.findAll().toList()
        val orderTest = all[0]
        assertAll(
            { assertEquals(order.id, orderTest.id)},
            { assertEquals(order.uuid, orderTest.uuid)},
            { assertEquals(order.status, orderTest.status) },
            { assertEquals(order.total, orderTest.total) },
            { assertEquals(order.iva, orderTest.iva)},
            { assertEquals(order.orderLine, orderTest.orderLine)},
            { assertEquals(order.cliente, orderTest.cliente)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        orderRepository.save(order)
        val orderTest = orderRepository.findById(order.id)
        assertAll(
            { assertEquals(order.id, orderTest.id)},
            { assertEquals(order.uuid, orderTest.uuid)},
            { assertEquals(order.status, orderTest.status) },
            { assertEquals(order.total, orderTest.total) },
            { assertEquals(order.iva, orderTest.iva)},
            { assertEquals(order.orderLine, orderTest.orderLine)},
            { assertEquals(order.cliente, orderTest.cliente)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save()= runTest {
        val orderTest = orderRepository.save(order)
        assertAll(
            { assertEquals(order.id, orderTest.id)},
            { assertEquals(order.uuid, orderTest.uuid)},
            { assertEquals(order.status, orderTest.status) },
            { assertEquals(order.total, orderTest.total) },
            { assertEquals(order.iva, orderTest.iva)},
            { assertEquals(order.orderLine, orderTest.orderLine)},
            { assertEquals(order.cliente, orderTest.cliente)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest{
        val orderTest = orderRepository.delete(order)
        assertAll(
            { assertTrue { orderTest }}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        orderRepository.save(order)
        val orderUpdate = Order(
            ObjectId("223456789912345678901232").toId(),
            UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
            Order.StatusOrder.DELIVERED,
            12.0,
            12.0,
            ObjectId("123456789912345678901232").toId(),
            UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
        )
        val orderTest = orderRepository.update(orderUpdate)
        assertAll(
            { assertEquals(order.id, orderTest.id)},
            { assertEquals(order.uuid, orderTest.uuid)},
            { assertEquals(order.status, orderTest.status) },
            { assertEquals(order.total, orderTest.total) },
            { assertEquals(order.iva, orderTest.iva)},
            { assertEquals(order.orderLine, orderTest.orderLine)},
            { assertEquals(order.cliente, orderTest.cliente)}
        )
    }
}