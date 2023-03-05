package biques.dam.es.services.sales

import biques.dam.es.dto.*
import de.jensklingenberg.ktorfit.http.*

interface KtorFitRestSales {
    @GET("/products&services/list")
    suspend fun getAll(
        @Header("Authorization") token: String
    ): List<SaleDTO>

    @GET("/products&services/appointments")
    suspend fun getAllAppointments(
        @Header("Authorization") token: String
    ): List<AppointmentDTO>

    @GET("/products&services/{id}")
    suspend fun getById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): List<SaleDTO>

    @GET("/products&services/appointments/{id}")
    suspend fun getAppointmentById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): AppointmentDTO

    @POST("/products&services")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body entity: SaleDTO
    ): SaleDTO

    @POST("/products&services/appointments")
    suspend fun createAppointments(
        @Header("Authorization") token: String,
        @Body entity: AppointmentCreateDTO
    ): AppointmentDTO

    @PUT("/products&services/{id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body entity: SaleDTO
    ): SaleDTO

    @PUT("/products&services/{id}")
    suspend fun updateAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body entity: AppointmentCreateDTO
    ): AppointmentDTO

    @DELETE("/products&services/{id}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Unit

    @DELETE("/products&services/appointments{id}")
    suspend fun deleteAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Unit
}