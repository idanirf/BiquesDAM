package biques.dam.es.repositories.order

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderException
import biques.dam.es.models.Order
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.reactivestreams.findOneById
import org.litote.kmongo.reactivestreams.getCollection

class OrderRepositoryImpl: OrderRepository {
    private val db = MongoDbManager.database
    override fun findAll(): Flow<Order> {
        return db.getCollection<Order>().find().toFlow()
    }

    override suspend fun findById(id: Id<Order>): Order? {
        return db.getCollection<Order>().findOneById(id) ?: throw OrderException("No existe el pedido")
    }

    override suspend fun save(entity: Order): Order? {
        return db.getCollection<Order>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Order): Boolean {
        return db.getCollection<Order>().deleteOneById(entity.id).let { true }
    }

    override suspend fun update(entity: Order): Order? {
        return db.getCollection<Order>().save(entity).let { entity }
    }
}