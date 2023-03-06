package biques.dam.es.repositories.users

import biques.dam.es.dto.*
import kotlinx.coroutines.flow.Flow

interface IUsersRepository {
    suspend fun findAll(token: String): List<UserResponseDTO>
    suspend fun findById(token: String, id: Long): UserResponseDTO
     suspend fun login(entity: UserLoginDTO): UserTokenDTO
    suspend fun register(entity: UserRegisterDTO): UserTokenDTO
    suspend fun update(token: String, id: Long, entity: UserUpdateDTO): UserResponseDTO
    suspend fun delete(token: String, id: Long)
}
