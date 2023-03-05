package biques.dam.es.repositories.orderline

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderLineException
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.models.Order
import biques.dam.es.models.OrderLine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import java.util.*

//TODO poner todo con withContext
class OrderLineRepositoryImpl: OrderLineRepository {
    private val db = MongoDbManager.database
    override suspend fun findByUUID(uuid: UUID): OrderLine {
        return db.getCollection<OrderLine>().findOne(OrderLine::uuid eq uuid) ?: throw OrderLineNotFoundException("The order line with uuid $uuid does not exist")
    }

    override fun findAll(): Flow<OrderLine> {
        return db.getCollection<OrderLine>().find().publisher.asFlow()
    }

    override suspend fun findById(id: Id<OrderLine>): OrderLine {
        return db.getCollection<OrderLine>().findOneById(id) ?: throw OrderLineNotFoundException("The order line with id $id does not exist")
    }

    override suspend fun save(entity: OrderLine): OrderLine {
        return db.getCollection<OrderLine>().save(entity).let { entity }
    }

    override suspend fun delete(entity: OrderLine): Boolean {
        val res = db.getCollection<OrderLine>().deleteOneById(entity.id)
        if (res.deletedCount.toInt() == 1){
            return true
        }else{
            throw OrderLineNotFoundException("The order line with id ${entity.id} does not exist")
        }
    }

    override suspend fun update(entity: OrderLine): OrderLine {
        val res =  db.getCollection<OrderLine>().updateOne(entity)
        if (res.modifiedCount.toInt() == 1){
            return entity
        }else{
            throw OrderLineNotFoundException("The order line with id ${entity.id} does not exist")
        }
    }

}