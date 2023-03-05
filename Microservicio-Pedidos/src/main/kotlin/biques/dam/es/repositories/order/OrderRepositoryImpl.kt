package biques.dam.es.repositories.order

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderException
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.models.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.findOneById
import org.litote.kmongo.reactivestreams.getCollection
import java.util.*

class OrderRepositoryImpl: OrderRepository {
    private val db = MongoDbManager.database
    override suspend fun findByUUID(uuid: UUID): Order {
        return db.getCollection<Order>().findOne(Order::uuid eq uuid)?: throw OrderNotFoundException("The order with uuid $uuid does not exist")
    }

    override fun findAll(): Flow<Order> {
        return db.getCollection<Order>().find().publisher.asFlow()
    }

    override suspend fun findById(id: Id<Order>): Order {
        return db.getCollection<Order>().findOneById(id) ?: throw OrderNotFoundException("The order with id $id does not exist")
    }

    override suspend fun save(entity: Order): Order {
        return db.getCollection<Order>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Order): Boolean {
        val res = db.getCollection<Order>().deleteOneById(entity.id)
        if (res.deletedCount.toInt() == 1){
            return true
        }else{
            throw OrderNotFoundException("The order with id ${entity.id} does not exist")
        }
    }

    override suspend fun update(entity: Order): Order {
        val res = db.getCollection<Order>().updateOne(entity)
        if (res.modifiedCount.toInt() > 0){
            return entity
        }else{
            throw OrderNotFoundException("The order with id ${entity.id} does not exist")
        }
    }
}