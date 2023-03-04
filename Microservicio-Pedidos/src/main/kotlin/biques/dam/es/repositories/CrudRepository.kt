package biques.dam.es.repositories

import biques.dam.es.models.OrderLine
import kotlinx.coroutines.flow.Flow


interface CrudRepository<T, ID> {
    fun findAll(): Flow<T>
    suspend fun findById(id: ID): T
    suspend fun save(entity: T): T
    suspend fun delete(entity: T): Boolean
    suspend fun update(entity: T): T
}