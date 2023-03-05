package biques.dam.es.repositories.appointment

import biques.dam.es.dto.AppointmentCreateDTO
import biques.dam.es.dto.AppointmentDTO
import biques.dam.es.exceptions.AppointmentConflictIntegrityException
import biques.dam.es.exceptions.AppointmentNotFoundException
import biques.dam.es.services.sales.KtorFitClientSales
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("KtorFitRepositoryAppointment")
class KtorFitRepositoryAppointment : IAppointmentRepository {
    private val client by lazy { KtorFitClientSales.instance }

    override suspend fun findAll(token: String): Flow<AppointmentDTO> = withContext(Dispatchers.IO) {
        try {
            return@withContext client.getAllAppointments(token).asFlow()
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error getting appointments: ${e.message}")
        }
    }

    override suspend fun delete(token: String, id: UUID) {
        try {
            client.deleteAppointment(token, id.toString())
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error deleting appointent with id $id : ${e.message}")
        }
    }

    override suspend fun update(token: String, id: UUID, entity: AppointmentCreateDTO): AppointmentDTO {
        try {
            return client.updateAppointment(token, id.toString(), entity)
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error updating appointment with id $id : ${e.message}")
        }
    }

    override suspend fun save(token: String, entity: AppointmentCreateDTO): AppointmentDTO {
        try {
           return client.createAppointments(token, entity)
        } catch (e: Exception) {
            throw AppointmentConflictIntegrityException("Error saving appointment $entity : ${e.message}")
        }
    }

    override suspend fun findById(token: String, id: UUID): AppointmentDTO {
        try {
            return client.getAppointmentById(token, id.toString())
        } catch (e: Exception) {
            throw AppointmentNotFoundException("Error getting appointment with id $id : ${e.message}")
        }
    }
}