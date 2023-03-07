package biques.dam.es.repositories.appointment

import biques.dam.es.dto.AppointmentCreateDTO
import biques.dam.es.dto.AppointmentDTO
import biques.dam.es.exceptions.AppointmentConflictIntegrityException
import biques.dam.es.exceptions.AppointmentNotFoundException
import biques.dam.es.services.sales.KtorFitClientSales
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

/**
 * Implementation of the IAppointmentRepository interface.
 * @author BiquesDAM-Team
 */
@Single
@Named("KtorFitRepositoryAppointment")
class KtorFitRepositoryAppointment : IAppointmentRepository {
    private val client by lazy { KtorFitClientSales.instance }

    /**
     * Returns a flow of all appointments.
     * @param token the access token for authentication.
     * @return a flow of all appointments in the database.
     * @throws AppointmentNotFoundException if the appointments are not found.
     */
    override suspend fun findAll(token: String): Flow<AppointmentDTO> = withContext(Dispatchers.IO) {
        val call = async {
            client.getAllAppointments(token).asFlow()
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error getting appointments: ${e.message}")
        }
    }

    /**
     * Deletes an appointment with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the appointment to delete.
     * @throws AppointmentNotFoundException if the appointment to delete is not found.
     */

    override suspend fun delete(token: String, id: UUID) = withContext(Dispatchers.IO) {
        val call = async {
            client.deleteAppointment(token, id.toString())
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error deleting appointent with id $id : ${e.message}")
        }
    }

    /**
     * Updates an existing appointment with the specified ID.
     * @param token the access token for authentication.
     * @param id the ID of the appointment to update.
     * @param entity the new appointment data to update.
     * @return the updated appointment object.
     * @throws AppointmentNotFoundException if the appointment to update is not found.
     */
    override suspend fun update(token: String, id: UUID, entity: AppointmentCreateDTO): AppointmentDTO = withContext(Dispatchers.IO){
        val call = async {
            client.updateAppointment(token, id.toString(), entity)
        }

        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error updating appointment with id $id : ${e.message}")
        }
    }

    /**
     * Saves a new appointment.
     * @param token the access token for authentication.
     * @param entity the new appointment to save.
     * @return the saved appointment object.
     * @throws AppointmentConflictIntegrityException if there is a conflict with the integrity of the appointment data.
     */

    override suspend fun save(token: String, entity: AppointmentCreateDTO): AppointmentDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.createAppointments(token, entity)
        }
        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw AppointmentConflictIntegrityException("Error saving appointment $entity : ${e.message}")
        }
    }

    /**
    * Finds an appointment by its ID.
    * @param token the access token for authentication.
    * @param id the ID of the appointment to find.
    * @return the appointment object
    */
    override suspend fun findById(token: String, id: UUID): AppointmentDTO = withContext(Dispatchers.IO) {
        val call = async {
            client.getAppointmentById(token, id.toString())
        }
        try {
            return@withContext call.await()
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error getting appointment with id $id : ${e.message}")
        }
    }
}