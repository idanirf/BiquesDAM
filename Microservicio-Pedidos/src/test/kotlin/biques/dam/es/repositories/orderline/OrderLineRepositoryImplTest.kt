package biques.dam.es.repositories.orderline

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderLineException
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.models.OrderLine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.litote.kmongo.id.toId
import org.litote.kmongo.reactivestreams.getCollection
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class OrderLineRepositoryImplTest {
    private val db = MongoDbManager.database.database
    private val orderLineRepository = OrderLineRepositoryImpl()
    private val linea_pedido = OrderLine(
        ObjectId("123456789912345678901232").toId(),
        UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
        UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
        3,
        5.0,
        15.0,
        UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
    )



    @BeforeEach
    fun setUp(): Unit = runBlocking {
        db.getCollection<OrderLine>().drop()
        orderLineRepository.save(linea_pedido)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest{
        orderLineRepository.save(linea_pedido)
        val all = orderLineRepository.findAll().toList()
        val lineaTest = all[0]

        assertAll(
            {assertEquals(1, all.size)},
            {assertFalse(all.isEmpty())},
            { assertEquals(linea_pedido.id, lineaTest.id) },
            { assertEquals(linea_pedido.uuid, lineaTest.uuid) },
            { assertEquals(linea_pedido.product, lineaTest.product) },
            { assertEquals(linea_pedido.amount, lineaTest.amount) },
            { assertEquals(linea_pedido.price, lineaTest.price) },
            { assertEquals(linea_pedido.total, lineaTest.total) },
            { assertEquals(linea_pedido.employee, lineaTest.employee) }

        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByUUID() = runTest {
        orderLineRepository.save(linea_pedido)
        val lineaTest = orderLineRepository.findByUUID(UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"))
        assertAll(
            { assertEquals(linea_pedido.id, lineaTest.id) },
            { assertEquals(linea_pedido.uuid, lineaTest.uuid) },
            { assertEquals(linea_pedido.product, lineaTest.product) },
            { assertEquals(linea_pedido.amount, lineaTest.amount) },
            { assertEquals(linea_pedido.price, lineaTest.price) },
            { assertEquals(linea_pedido.total, lineaTest.total) },
            { assertEquals(linea_pedido.employee, lineaTest.employee) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByUUIDNotExist() = runTest{
        val id =UUID.fromString("1f85b14d-bdae-421a-9e41-87fbdaef9cff")
        assertAll(
            {
                val res = runBlocking {
                    assertThrows<OrderLineNotFoundException> { orderLineRepository.findByUUID(id) }
                }
                assertEquals("The order line with uuid $id does not exist", res.message)
            },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        orderLineRepository.save(linea_pedido)
        val lineaTest = orderLineRepository.findById(linea_pedido.id)
        assertAll(
            { assertEquals(linea_pedido.id, lineaTest.id) },
            { assertEquals(linea_pedido.uuid, lineaTest.uuid) },
            { assertEquals(linea_pedido.product, lineaTest.product) },
            { assertEquals(linea_pedido.amount, lineaTest.amount) },
            { assertEquals(linea_pedido.price, lineaTest.price) },
            { assertEquals(linea_pedido.total, lineaTest.total) },
            { assertEquals(linea_pedido.employee, lineaTest.employee) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExist() = runTest {
        val id = ObjectId("923456789912345678900232").toId<OrderLine>()
        assertAll(
            {
                val res = runBlocking {
                    assertThrows<OrderLineNotFoundException> { orderLineRepository.findById(id) }
                }
                assertEquals("The order line with id $id does not exist", res.message)
            },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val lineaTest = orderLineRepository.save(linea_pedido)
        assertAll(
            { assertEquals(linea_pedido.id, lineaTest.id) },
            { assertEquals(linea_pedido.uuid, lineaTest.uuid) },
            { assertEquals(linea_pedido.product, lineaTest.product) },
            { assertEquals(linea_pedido.amount, lineaTest.amount) },
            { assertEquals(linea_pedido.price, lineaTest.price) },
            { assertEquals(linea_pedido.total, lineaTest.total) },
            { assertEquals(linea_pedido.employee, lineaTest.employee) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        orderLineRepository.save(linea_pedido)
        val lineaPedidoUpdate = OrderLine(
            ObjectId("123456789912345678901232").toId(),
            UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
            UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
            1,
            6.0,
            6.0,
            UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
        )
        val lineaTest = orderLineRepository.update(lineaPedidoUpdate)
        assertAll(
            { assertEquals(lineaPedidoUpdate.id, lineaTest.id) },
            { assertEquals(lineaPedidoUpdate.uuid, lineaTest.uuid) },
            { assertEquals(linea_pedido.product, lineaTest.product) },
            { assertEquals(lineaPedidoUpdate.amount, lineaTest.amount) },
            { assertEquals(lineaPedidoUpdate.price, lineaTest.price) },
            { assertEquals(lineaPedidoUpdate.total, lineaTest.total) },
            { assertEquals(linea_pedido.employee, lineaTest.employee) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateNotExist() = runTest{
        val lineaPedidoUpdate = OrderLine(
            ObjectId("423456789912345678901232").toId(),
            UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
            UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
            1,
            6.0,
            6.0,
            UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
        )
        val id = ObjectId("423456789912345678901232").toId<OrderLine>()
        assertAll(
            {
                val res = runBlocking {
                    assertThrows<OrderLineNotFoundException> { orderLineRepository.update(lineaPedidoUpdate) }
                }
                assertEquals("The order line with id $id does not exist", res.message)
            },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val lineaTest = orderLineRepository.delete(linea_pedido)
        assertAll(
            { assertTrue { lineaTest } }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNotExist() = runTest{
        val lineaPedidoDelete = OrderLine(
            ObjectId("323456789912345678901232").toId(),
            UUID.fromString("4d83b14d-bdae-422a-9e41-87fbdaef9cff"),
            UUID.fromString("0c34f158-5b8e-4193-9ad3-f00e7be93352"),
            1,
            6.0,
            6.0,
            UUID.fromString("fcf9e6bb-6ff1-4aae-8b50-0d3286b20f81")
        )
        val id = ObjectId("323456789912345678901232").toId<OrderLine>()
        assertAll(
            {
                val res = runBlocking {
                    assertThrows<OrderLineNotFoundException> { orderLineRepository.delete(lineaPedidoDelete) }
                }
                assertEquals("The order line with id $id does not exist", res.message)
            },
        )
    }


}