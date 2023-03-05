package biques.dam.es.repositories.sales

import kotlinx.coroutines.flow.Flow

interface ISalesRepository <T, ID> {
    suspend fun findAll(token: String): Flow<T>
    suspend fun findById(token: String, id: ID): T
    suspend fun save(token: String, entity: T): T
    suspend fun update(token: String, id: ID, entity: T): T
    suspend fun delete(token: String, id: ID)
}

