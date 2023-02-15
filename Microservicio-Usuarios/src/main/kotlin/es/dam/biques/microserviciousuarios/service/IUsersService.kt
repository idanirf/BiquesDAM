package es.dam.biques.microserviciousuarios.service

import es.dam.biques.microserviciousuarios.models.User
import kotlinx.coroutines.flow.Flow

interface IUsersService {
    suspend fun findAll(): Flow<User>
    suspend fun findById(id: Long): User?
    suspend fun findByEmail(email: String): User?
    suspend fun save(user: User): User
    suspend fun update(id: Long, user: User): User?
    suspend fun delete(user: User)
    suspend fun deleteById(id: Long)
}