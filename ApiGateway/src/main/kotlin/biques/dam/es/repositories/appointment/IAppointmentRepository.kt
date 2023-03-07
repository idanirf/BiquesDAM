package biques.dam.es.repositories.appointment

import biques.dam.es.dto.AppointmentCreateDTO
import biques.dam.es.dto.AppointmentDTO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * This interface represents the repository for the Appointment model.
 * @author BiquesDAM-Team
 */

interface IAppointmentRepository {
    /**
     * Find all appointments
     * @param token JWT token
     * @return Flow of appointments
     */
    suspend fun findAll(token: String): Flow<AppointmentDTO>

    /**
     * Find appointment by id
     * @param token JWT token
     * @param id Appointment uuid
     * @return Appointment
     */
    suspend fun findById(token: String, id: UUID): AppointmentDTO

    /**
     * Create a new appointment
     * @param token JWT token
     * @param dto AppointmentCreateDTO
     * @return AppointmentDTO
     */
    suspend fun save(token: String, entity: AppointmentCreateDTO): AppointmentDTO

    /**
     * Update an appointment
     * @param token JWT token
     * @param id Appointment uuid
     * @param dto AppointmentCreateDTO
     * @return AppointmentDTO
     */
    suspend fun update(token: String, id: UUID, entity: AppointmentCreateDTO): AppointmentDTO

    /**
     * Delete an appointment
     * @param token JWT token
     * @param id Appointment uuid
     * @return AppointmentDTO
     */
    suspend fun delete(token: String, id: UUID)
}