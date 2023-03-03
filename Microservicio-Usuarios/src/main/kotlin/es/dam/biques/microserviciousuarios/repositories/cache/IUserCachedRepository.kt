package es.dam.biques.microserviciousuarios.repositories.cache

import es.dam.biques.microserviciousuarios.models.User
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IUserCachedRepository {
    suspend fun findAll(): Flow<User>
    suspend fun findById(id: Long): User?
    suspend fun findByEmail(email: String): User?
    suspend fun findByUUID(uuid: UUID): User?
    suspend fun save(user: User): User
    suspend fun update(id: Long, user: User): User?
    suspend fun deleteById(id: Long): User?
}