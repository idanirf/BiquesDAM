package biques.dam.es.repositories.orderline

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderLineException
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.models.Order
import biques.dam.es.models.OrderLine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import java.util.*

/**
 * Implementation of the OrderLineRepository interface that uses a MongoDB database as the data source.
 * @author BiquesDAM-Team
 */

class OrderLineRepositoryImpl: OrderLineRepository {
    private val db = MongoDbManager.database

    /**
     * Finds an OrderLine in the database by its UUID.
     * @param uuid the UUID of the order line to find.
     * @return the OrderLine object corresponding to the specified UUID.
     * @throws OrderLineNotFoundException if the order line with the specified UUID is not found in the database.
     */
    override suspend fun findByUUID(uuid: UUID): OrderLine {
        return db.getCollection<OrderLine>().find().publisher.asFlow().toList().firstOrNull { it.uuid == uuid } ?: throw OrderLineNotFoundException("The order line with uuid $uuid does not exist")
    }


    /**
     * Returns a Flow containing all OrderLine objects in the database.
     * @return a Flow containing all OrderLine objects in the database.
     */
    override fun findAll(): Flow<OrderLine> {
        return db.getCollection<OrderLine>().find().publisher.asFlow()
    }

    /**
     * Finds an OrderLine in the database by its ID.
     * @param id the ID of the order line to find.
     * @return the OrderLine object corresponding to the specified ID.
     * @throws OrderLineNotFoundException if the order line with the specified ID is not found in the database.
     */
    override suspend fun findById(id: Id<OrderLine>): OrderLine {
        return db.getCollection<OrderLine>().findOneById(id) ?: throw OrderLineNotFoundException("The order line with id $id does not exist")
    }

    /**
     * Saves an OrderLine object in the database.
     * @param entity the OrderLine object to save.
     * @return the OrderLine object saved in the database.
     */
    override suspend fun save(entity: OrderLine): OrderLine {
        return db.getCollection<OrderLine>().save(entity).let { entity }
    }

    /**
     * Deletes an OrderLine object from the database.
     * @param entity the OrderLine object to delete.
     * @return true if the OrderLine object was deleted successfully, false otherwise.
     * @throws OrderLineNotFoundException if the order line with the specified ID is not found in the database.
     */
    override suspend fun delete(entity: OrderLine): Boolean {
        val res = db.getCollection<OrderLine>().deleteOneById(entity.id)
        if (res.deletedCount.toInt() == 1){
            return true
        }else{
            throw OrderLineNotFoundException("The order line with id ${entity.id} does not exist")
        }
    }

    /**
     * Updates an OrderLine object in the database.
     * @param entity the OrderLine object to update.
     * @return the OrderLine object updated in the database.
     * @throws OrderLineNotFoundException if the order line with the specified ID is not found in the database.
     */
    override suspend fun update(entity: OrderLine): OrderLine {
        val res =  db.getCollection<OrderLine>().updateOne(entity)
        if (res.modifiedCount.toInt() == 1){
            return entity
        }else{
            throw OrderLineNotFoundException("The order line with id ${entity.id} does not exist")
        }
    }

}