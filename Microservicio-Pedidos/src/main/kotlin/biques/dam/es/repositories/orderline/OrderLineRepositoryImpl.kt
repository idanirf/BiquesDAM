package biques.dam.es.repositories.orderline

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderLineException
import biques.dam.es.models.OrderLine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.litote.kmongo.Id


class OrderLineRepositoryImpl: OrderLineRepository {
    private val db = MongoDbManager.database


    override fun findAll(): Flow<OrderLine> {
        return db.getCollection<OrderLine>().find().publisher.asFlow()
    }

    override suspend fun findById(id: Id<OrderLine>): OrderLine? {
        return db.getCollection<OrderLine>().findOneById(id) ?: throw OrderLineException("The order line with id $id does not exist")
    }

    override suspend fun save(entity: OrderLine): OrderLine? {
        return db.getCollection<OrderLine>().save(entity).let { entity }
    }

    override suspend fun delete(entity: OrderLine): Boolean {
        return db.getCollection<OrderLine>().deleteOneById(entity.id).let { true }
    }

    override suspend fun update(entity: OrderLine): OrderLine? {
        return db.getCollection<OrderLine>().save(entity).let { entity }
    }

}