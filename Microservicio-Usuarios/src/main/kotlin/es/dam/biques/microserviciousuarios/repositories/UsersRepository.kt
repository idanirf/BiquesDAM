package es.dam.biques.microserviciousuarios.repositories

import es.dam.biques.microserviciousuarios.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CrudRepository<User, Long> {
    fun findUsersByEmail(email: String): User?
}