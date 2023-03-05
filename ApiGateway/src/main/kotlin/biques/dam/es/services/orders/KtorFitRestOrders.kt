package biques.dam.es.services.orders

import biques.dam.es.dto.*
import de.jensklingenberg.ktorfit.http.*

interface KtorFitRestOrders {
    @GET("order")
    suspend fun getAllOrder(
        @Header("Authorization") token: String
    ): List<OrderDTO>

    @GET("order/{id}")
    suspend fun getByIdOrder(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OrderDTO

    @POST("order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body order: OrderSaveDTO
    ): OrderDTO

    @PUT("order/{id}")
    suspend fun updateOrder(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body user: OrderDTOUpdate
    ): OrderDTO

    @DELETE("order/{id}")
    suspend fun deleteOrder(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Unit

    @GET("orderline")
    suspend fun getAllOrderLine(
        @Header("Authorization") token: String
    ): List<OrderLineDTO>

    @GET("orderline/{id}")
    suspend fun getByIdOrderLine(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OrderLineDTO

    @POST("orderline")
    suspend fun createOrderLine(
        @Header("Authorization") token: String,
        @Body order: OrderLineCreateDTO
    ): OrderLineDTO

    @PUT("orderline/{id}")
    suspend fun updateOrderLine(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body user: OrderLineUpdateDTO
    ): OrderLineDTO

    @DELETE("orderline/{id}")
    suspend fun deleteOrderLine(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )
}