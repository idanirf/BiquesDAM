package biques.dam.es.services.orders

import biques.dam.es.dto.*
import de.jensklingenberg.ktorfit.http.*

/**
 * Interface that defines the methods to communicate with the server for the Orders API
 * @author The BiquesDAM Team
 */
interface KtorFitRestOrders {

    /**
     * Gets all the orders
     * @param token The token of the user
     * @return A list of orders
     * @author The BiquesDAM Team
     */
    @GET("order")
    suspend fun getAllOrder(
        @Header("Authorization") token: String
    ): OrderAllDTO

    /**
     * Gets an order by its id
     * @param token The token of the user
     * @param id The id of the order
     * @return The order with the given id, or null if it doesn't exist
     * @author The BiquesDAM Team
     */
    @GET("order/{id}")
    suspend fun getByIdOrder(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OrderDTO

    /**
     * Creates an order
     * @param token The token of the user
     * @param order The order to create
     * @return The created order, or null if it couldn't be created
     * @author The BiquesDAM Team
     */
    @POST("order")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body order: OrderSaveDTO
    ): OrderDTO

    /**
     * Updates an order
     * @param token The token of the user
     * @param id The id of the order to update
     * @param user The order to update
     * @return The updated order, or null if it couldn't be updated
     * @author The BiquesDAM Team
     */
    @PUT("order/{id}")
    suspend fun updateOrder(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body user: OrderDTOUpdate
    ): OrderDTO

    /**
     * Deletes an order
     * @param token The token of the user
     * @param id The id of the order to delete
     * @return The deleted order, or null if it couldn't be deleted
     */
    @DELETE("order/{id}")
    suspend fun deleteOrder(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Unit

    /**
     * Gets all the order lines
     * @param token The token of the user
     * @return A list of order lines
     */
    @GET("orderline")
    suspend fun getAllOrderLine(
        @Header("Authorization") token: String
    ): List<OrderLineDTO>

    /**
     * Gets an order line by its id
     * @param token The token of the user
     * @param id The id of the order line
     * @return The order line with the given id, or null if it doesn't exist
     */
    @GET("orderline/{id}")
    suspend fun getByIdOrderLine(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OrderLineDTO

    /**
     * Creates an order line
     * @param token The token of the user
     * @param order The order line to create
     * @return The created order line, or null if it couldn't be created
     */
    @POST("orderline")
    suspend fun createOrderLine(
        @Header("Authorization") token: String,
        @Body order: OrderLineCreateDTO
    ): OrderLineDTO

    /**
     * Updates an order line
     * @param token The token of the user
     * @param id The id of the order line to update
     * @param user The order line to update
     * @return The updated order line, or null if it couldn't be updated
     */
    @PUT("orderline/{id}")
    suspend fun updateOrderLine(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body user: OrderLineUpdateDTO
    ): OrderLineDTO

    /**
     * Deletes an order line
     * @param token The token of the user
     * @param id The id of the order line to delete
     * @return The deleted order line, or null if it couldn't be deleted
     */
    @DELETE("orderline/{id}")
    suspend fun deleteOrderLine(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )
}