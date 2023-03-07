package biques.dam.es.repositories.order

import biques.dam.es.db.MongoDbManager
import biques.dam.es.exceptions.OrderException
import biques.dam.es.exceptions.OrderLineNotFoundException
import biques.dam.es.exceptions.OrderNotFoundException
import biques.dam.es.models.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.findOneById
import org.litote.kmongo.reactivestreams.getCollection
import java.util.*

/**
 * Implementation of the OrderRepository interface that uses a MongoDB database as the data source.
 * @author BiquesDAM-Team
 */
class OrderRepositoryImpl: OrderRepository {
    private val db = MongoDbManager.database
    /**
     * Finds an Order in the database by its UUID.
     * @param uuid the UUID of the order to find.
     * @return the Order object corresponding to the specified UUID.
     * @throws OrderNotFoundException if the order with the specified UUID is not found in the database.
     */
    override suspend fun findByUUID(uuid: UUID): Order {
        return db.getCollection<Order>().find().publisher.asFlow().toList().firstOrNull { it.uuid == uuid }?: throw OrderNotFoundException("The order with uuid $uuid does not exist")
    }

    /**
     * Returns a Flow containing all Order objects in the database.
     * @return a Flow containing all Order objects in the database.
     */
    override fun findAll(): Flow<Order> {
        return db.getCollection<Order>().find().publisher.asFlow()
    }

    /**
     * Finds an Order in the database by its ID.
     * @param id the ID of the order to find.
     * @return the Order object corresponding to the specified ID.
     * @throws OrderNotFoundException if the order with the specified ID is not found in the database.
     */
    override suspend fun findById(id: Id<Order>): Order {
        return db.getCollection<Order>().findOneById(id) ?: throw OrderNotFoundException("The order with id $id does not exist")
    }

    /**
     * Saves an Order object in the database.
     * @param entity the Order object to save.
     * @return the saved Order object.
     */
    override suspend fun save(entity: Order): Order {
        return db.getCollection<Order>().save(entity).let { entity }
    }

    /**
     * Deletes an Order object from the database.
     * @param entity the Order object to delete.
     * @return true if the Order object was successfully deleted, false otherwise.
     * @throws OrderNotFoundException if the order with the specified ID is not found in the database.
     */
    override suspend fun delete(entity: Order): Boolean {
        val res = db.getCollection<Order>().deleteOneById(entity.id)
        if (res.deletedCount.toInt() == 1){
            return true
        }else{
            throw OrderNotFoundException("The order with id ${entity.id} does not exist")
        }
    }

    /**
     * Updates an Order object in the database.
     * @param entity the Order object to update.
     * @return the updated Order object.
     * @throws OrderNotFoundException if the order with the specified ID is not found in the database.
     */
    override suspend fun update(entity: Order): Order {
        val res = db.getCollection<Order>().updateOne(entity)
        if (res.modifiedCount.toInt() > 0){
            return entity
        }else{
            throw OrderNotFoundException("The order with id ${entity.id} does not exist")
        }
    }
}