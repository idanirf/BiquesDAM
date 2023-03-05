package biques.dam.es.repositories.appointment

import biques.dam.es.dto.AppointmentCreateDTO
import biques.dam.es.dto.AppointmentDTO
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface IAppointmentRepository {
    suspend fun findAll(token: String): Flow<AppointmentDTO>
    suspend fun findById(token: String, id: UUID): AppointmentDTO
    suspend fun save(token: String, entity: AppointmentCreateDTO): AppointmentDTO
    suspend fun update(token: String, id: UUID, entity: AppointmentCreateDTO): AppointmentDTO
    suspend fun delete(token: String, id: UUID)
}