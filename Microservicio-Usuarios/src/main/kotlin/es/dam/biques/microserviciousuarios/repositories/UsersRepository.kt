package es.dam.biques.microserviciousuarios.repositories

import es.dam.biques.microserviciousuarios.models.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CoroutineCrudRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * @param email the email address of the user to find
     * @return a Flow of User objects that match the given email address
     * @throws RuntimeException if there is an error while accessing the database
     * @author BiquesDAM-Team
     */
    fun findUserByEmail(email: String): Flow<User>

    /**
     * Finds a user by their UUID.
     * @param uuid the UUID of the user to find
     * @return a Flow of User objects that match the given UUID
     * @throws RuntimeException if there is an error while accessing the database
     * @author BiquesDAM-Team
     */
    fun findUserByUuid(uuid: String): Flow<User>

    /**
     * Finds a user by their username.
     * @param username the username of the user to find
     * @return a Flow of User objects that match the given username
     * @throws RuntimeException if there is an error while accessing the database
     * @author BiquesDAM-Team
     */
    fun findUserByUsername(username: String): Flow<User>
}