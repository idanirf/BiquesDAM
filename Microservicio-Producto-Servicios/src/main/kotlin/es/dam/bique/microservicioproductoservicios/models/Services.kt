package es.dam.bique.microservicioproductoservicios.models

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("SERVICES")
data class Services(

    @Id
    val id: Long?,

    val uuid: UUID = UUID.randomUUID(),

    @NotEmpty(message = "The image cannot be empty.")
    val image: String,

    @Min(value = 0, message = "The price cannot be negative.")
    val price: Float = 0.0f,

    //APPOINTMENT
    val appointment: Appointment,

    @NotEmpty(message = "The type cannot be empty.")
    val type: ProductType

): OnSale