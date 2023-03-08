package biques.dam.es.services.sales

import biques.dam.es.dto.*
import de.jensklingenberg.ktorfit.http.*

/**
 * Interface that defines the methods to communicate with the server for the Sales API
 * @author The BiquesDAM Team
 */
interface KtorFitRestSales {

    /**
     * Gets all the sales
     * @param token The token of the user
     * @return A list of AllSaleDTO
     * @author The BiquesDAM Team
     */
    @GET("products&services/list")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): AllSaleDTO

    /**
     * Gets all the appointments
     * @param token The token of the user
     * @return A list of AppointmentDTO
     * @author The BiquesDAM Team
     */
    @GET("products&services/appointments")
    suspend fun getAllAppointments(
        @Header("Authorization") token: String
    ): List<AppointmentDTO>

    /**
     * Gets a sale by its id
     * @param token The token of the user
     * @param id The id of the sale
     * @return The AllSaleDTO with the given id
     * @author The BiquesDAM Team
     */
    @GET("products&services/{id}")
    suspend fun getById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): AllSaleDTO

    /**
     * Gets an appointment by its id
     * @param token The token of the user
     * @param id The id of the appointment
     * @return The AppointmentDTO with the given id
     * @author The BiquesDAM Team
     */
    @GET("products&services/appointments/{id}")
    suspend fun getAppointmentById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): AppointmentDTO

    /**
     * Creates a sale
     * @param token The token of the user
     * @param entity The sale to create
     * @return The created SaleDTO
     * @author The BiquesDAM Team
     */
    @POST("products&services")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body entity: SaleCreateDTO
    ): SaleDTO

    /**
     * Creates an appointment
     * @param token The token of the user
     * @param entity The appointment to create
     * @return The created AppointmentDTO
     * @author The BiquesDAM Team
     */
    @POST("products&services/appointments")
    suspend fun createAppointments(
        @Header("Authorization") token: String,
        @Body entity: AppointmentCreateDTO
    ): AppointmentDTO

    /**
     * Updates a sale
     * @param token The token of the user
     * @param id The id of the sale to update
     * @param entity The sale to update
     * @return The updated SaleDTO
     * @author The BiquesDAM Team
     */
    @PUT("products&services/{id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body entity: SaleCreateDTO
    ): SaleDTO

    /**
     * Updates an appointment
     * @param token The token of the user
     * @param id The id of the appointment to update
     * @param entity The appointment to update
     * @return The updated AppointmentDTO
     * @author The BiquesDAM Team
     */
    @PUT("products&services/{id}")
    suspend fun updateAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body entity: AppointmentCreateDTO
    ): AppointmentDTO

    /**
     * Deletes a sale
     * @param token The token of the user
     * @param id The id of the sale to delete
     * @return Unit (void) if the sale was deleted successfully
     * @author The BiquesDAM Team
     */
    @DELETE("products&services/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Unit

    /**
     * Deletes an appointment
     * @param token The token of the user
     * @param id The id of the appointment to delete
     * @return Unit (void) if the appointment was deleted successfully
     * @author The BiquesDAM Team
     */
    @DELETE("products&services/appointments{id}")
    suspend fun deleteAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Unit
}