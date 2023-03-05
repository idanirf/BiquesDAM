package es.dam.biques.microserviciousuarios.repositories

import es.dam.biques.microserviciousuarios.models.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CoroutineCrudRepository<User, Long> {
    fun findUserByEmail(email: String): Flow<User>
    fun findUserByUuid(uuid: String): Flow<User>
    fun findUserByUsername(username: String): Flow<User>
}