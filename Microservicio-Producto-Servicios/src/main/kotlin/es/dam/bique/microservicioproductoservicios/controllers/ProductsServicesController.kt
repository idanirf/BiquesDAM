package es.dam.bique.microservicioproductoservicios.controllers

import es.dam.bique.microservicioproductoservicios.dto.*
import es.dam.bique.microservicioproductoservicios.exceptions.*
import es.dam.bique.microservicioproductoservicios.mappers.toDTO
import es.dam.bique.microservicioproductoservicios.mappers.toModel
import es.dam.bique.microservicioproductoservicios.mappers.toOnSaleDTO
import es.dam.bique.microservicioproductoservicios.services.appointments.AppointmentService
import es.dam.bique.microservicioproductoservicios.services.products.ProductsService
import es.dam.bique.microservicioproductoservicios.services.services.ServicesService
import es.dam.bique.microservicioproductoservicios.validators.validate
import jakarta.validation.Valid
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Controller to manage products, services and appointments via an HTTP request
 * @param productsService service to manage products
 * @param appointmentService service to manage appointments
 * @param servicesService service to manage services
 * @author The BiquesDAM Team
 */
@RestController
@RequestMapping("/products&services")
class ProductsServicesController
    @Autowired constructor(
        private val productsService: ProductsService,
        private val appointmentService: AppointmentService,
        private val servicesService: ServicesService
    )  {

    /**
     * Get all products and services
     * @return list of products and services
     * @author The BiquesDAM Team
     */
    @GetMapping("/list")
    suspend fun findAll(): ResponseEntity<MutableList<OnSaleDTO>> {

        logger.info {" On sale controller - findAll() "}

        val resProducts = productsService.findAll().toList()
            .map { it.toDTO().toOnSaleDTO() }

        val resServices = servicesService.findAll().toList()
           .map { it.toDTO().toOnSaleDTO() }

        val res : MutableList<OnSaleDTO> = mutableListOf()

        resProducts.forEach {
            res.add(it)
        }

        resServices.forEach {
            res.add(it)
        }

        return ResponseEntity.ok(res)

    }

    /**
     * Get all appointments
     * @return list of appointments
     * @author The BiquesDAM Team
     */
    @GetMapping("/appointments")
    suspend fun findAllAppointments(): ResponseEntity<List<AppointmentDTO>> {
        logger.info {" On sale controller - findAll appointments() "}

        val res = appointmentService.findAll().toList()
            .map { it.toDTO() }

        return ResponseEntity.ok(res)

    }

    /**
     * Get OnSaleDTO by id
     * @param id id of the product or service
     * @return product or service with the id passed as parameter
     * @author The BiquesDAM Team
     */
    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: Long): ResponseEntity<MutableList<OnSaleDTO>> {

        logger.info { "On sale controller - findById(): $id" }

        val res : MutableList<OnSaleDTO> = mutableListOf()

        val product: OnSaleDTO? = try {
            productsService.findById(id).toDTO().toOnSaleDTO()
        } catch (e: ProductNotFoundException) {
            null
        }

        if (product != null) { res.add(product) }

        val service: OnSaleDTO? = try {
            servicesService.findById(id).toDTO().toOnSaleDTO()
        } catch (e: OnSaleNotFoundException) {
            null
        }

        if (service != null) { res.add(service) }
        if (res.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Nothing found for id: $id")

        }

        return ResponseEntity.ok(res)

    }

    /**
     * Get AppointmentDTO by id
     * @param id id of the appointment
     * @return appointment with the id passed as parameter
     * @author The BiquesDAM Team
     */
    @GetMapping("/appointments/{id}")
    suspend fun findAppointmentById(@PathVariable id: Long): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - findAppointmentById(): $id" }

        try {
            val entity = appointmentService.findById(id)
            val result = entity.toDTO()
            return ResponseEntity.ok(result)
        } catch (e: AppointmentNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    /**
     * Create a product or service
     * @param entityDto product or service to create
     * @return product or service created
     * @throws ResponseStatusException if the product or service is not valid, or it already exists
     * @author The BiquesDAM Team
     */
    @PostMapping("")
    suspend fun create(@Valid @RequestBody entityDto: OnSaleCreateDTO): ResponseEntity<OnSaleDTO> {

        logger.info { "On sale controller - OnSale create()"}

        if(entityDto.type.value == "PRODUCT"){
            try{
                val rep = entityDto.productEntity?.validate()?.toModel(UUID.randomUUID())
                val res = rep?.let { productsService.save(it).toDTO().toOnSaleDTO() }
                return ResponseEntity.status(HttpStatus.CREATED).body(res)

            } catch (e: ProductBadRequestException) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
            }
        }else{
            try {
                val rep = entityDto.serviceEntity?.validate()?.toModel(UUID.randomUUID())
                val res = rep?.let { servicesService.save(it).toDTO().toOnSaleDTO() }

                return ResponseEntity.status(HttpStatus.CREATED).body(res)
            } catch (e: ServiceBadRequestException) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
            }
        }

    }

    /**
     * Create an appointment
     * @param entityDto appointment to create
     * @return appointment created
     * @throws ResponseStatusException if the appointment is not valid, or it already exists
     * @author The BiquesDAM Team
     */
    @PostMapping("/appointments")
    suspend fun create(@Valid @RequestBody entityDto: AppointmentCreateDTO): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - appointment create()"}

        try{
            val rep = entityDto.validate().toModel(UUID.randomUUID())
            val res = appointmentService.save(rep).toDTO()

            return ResponseEntity.status(HttpStatus.CREATED).body(res)

        } catch (e: ProductBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }

    }

    /**
     * Update a product or service
     * @param uuid id of the product or service to update
     * @param entityDto product or service to update
     * @return product or service updated
     * @throws ResponseStatusException if the product or service is not valid, or it does not exist
     * @author The BiquesDAM Team
     */
    @PutMapping("/{uuid}")
    suspend fun update(@PathVariable uuid: UUID, @Valid @RequestBody entityDto: OnSaleCreateDTO): ResponseEntity<OnSaleDTO> {

        logger.info { "On sale controller - OnSale update() : $uuid" }

        if(entityDto.type.value == "PRODUCT"){
            try {

                val rep = entityDto.productEntity?.validate()?.toModel(uuid)
                val res = rep?.let { productsService.update(it).toDTO().toOnSaleDTO() }

                return ResponseEntity.status(HttpStatus.OK).body(res)

            } catch (e: ProductNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
            }

        }else{
            try {
                val rep = entityDto.serviceEntity?.validate()?.toModel(uuid)
                val res = rep?.let { servicesService.update(it).toDTO().toOnSaleDTO() }

                return ResponseEntity.status(HttpStatus.OK).body(res)

            } catch (e: ServiceNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
            }
        }
    }

    /**
     * Update an appointment
     * @param uuid id of the appointment to update
     * @param entityDTO appointment to update
     * @return appointment updated
     * @throws ResponseStatusException if the appointment is not valid, or it does not exist
     * @author The BiquesDAM Team
     */
    @PutMapping("/appointments/{uuid}")
    suspend fun update(@PathVariable uuid: UUID, @Valid @RequestBody entityDTO: AppointmentCreateDTO): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - appointment update() : $uuid" }

        try {

            val rep = entityDTO.validate().toModel(uuid)
            val res = appointmentService.update(rep).toDTO()

            return ResponseEntity.status(HttpStatus.OK).body(res)

        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    /**
     * Delete a product or service
     * @param uuid id of the product or service to delete
     * @return product or service deleted
     * @throws ResponseStatusException if the product or service does not exist
     * @author The BiquesDAM Team
     */
    @DeleteMapping("/{uuid}")
    suspend fun delete(@PathVariable uuid: UUID): ResponseEntity<OnSaleDTO> {

        logger.info { "On sale controller - delete() : $uuid" }

        try {
            productsService.delete(productsService.findByUuid(uuid)).toDTO().toOnSaleDTO()

        } catch (e: ProductNotFoundException) {
            try {
                servicesService.delete(servicesService.findByUuid(uuid)).toDTO().toOnSaleDTO()

            } catch (e: ServiceNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
            }
        }

        return ResponseEntity.noContent().build()

    }

    /**
     * Delete an appointment
     * @param uuid id of the appointment to delete
     * @return appointment deleted
     * @throws ResponseStatusException if the appointment does not exist
     * @author The BiquesDAM Team
     */
    @DeleteMapping("/appointments/{uuid}")
    suspend fun deleteAppointment(@PathVariable uuid: UUID): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - deleteAppointment() : $uuid" }

        try {
            appointmentService.delete(appointmentService.findByUuid(uuid))
            return ResponseEntity.noContent().build()
        } catch (e: AppointmentNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }

    }
}