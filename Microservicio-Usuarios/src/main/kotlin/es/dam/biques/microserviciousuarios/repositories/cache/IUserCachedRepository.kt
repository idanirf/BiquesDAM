package es.dam.biques.microserviciousuarios.repositories.cache

import es.dam.biques.microserviciousuarios.models.User
import kotlinx.coroutines.flow.Flow

interface IUserCachedRepository {
    /**
     *  Returns a flow of all users.
     *  @return a flow of all users.
     *  @author BiquesDAM-Team
     */
    suspend fun findAll(): Flow<User>

    /**
     *  Returns a user by its id.
     *  @param id the id of the user to be returned.
     *  @return the user with the given id or null if it doesn't exist.
     *  @author BiquesDAM-Team
     */
    suspend fun findById(id: Long): User?

    /**
     *  Returns a user by its email.
     *  @param email the email of the user to be returned.
     *  @return the user with the given email or null if it doesn't exist.
     *  @author BiquesDAM-Team
     */
    suspend fun findByEmail(email: String): User?

    /**
     *  Returns a user by its login.
     *  @param login the login of the user to be returned.
     *  @return the user with the given login or null if it doesn't exist.
     *  @author BiquesDAM-Team
     */
    suspend fun findByUUID(uuid: String): User?

    /**
     *  Returns a user by its phone number.
     *  @param phoneNumber the phone number of the user to be returned.
     *  @return the user with the given phone number or null if it doesn't exist.
     *  @author BiquesDAM-Team
     */
    suspend fun save(user: User): User

    /**
     *  Removes a user by its id.
     *  @param id the id of the user to be removed.
     *  @author BiquesDAM-Team
     */
    suspend fun update(id: Long, user: User): User?

    /**
     *  Removes a user by its email.
     *  @param email the email of the user to be removed.
     *  @author BiquesDAM-Team
     */
    suspend fun deleteById(id: Long): User?
}