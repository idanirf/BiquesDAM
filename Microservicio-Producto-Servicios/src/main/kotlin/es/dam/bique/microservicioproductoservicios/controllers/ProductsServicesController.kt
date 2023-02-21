package es.dam.bique.microservicioproductoservicios.controllers

import es.dam.bique.microservicioproductoservicios.dto.*
import es.dam.bique.microservicioproductoservicios.exceptions.*
import es.dam.bique.microservicioproductoservicios.mappers.toDTO
import es.dam.bique.microservicioproductoservicios.mappers.toModel
import es.dam.bique.microservicioproductoservicios.mappers.toOnSaleDTO
import es.dam.bique.microservicioproductoservicios.models.OnSale
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

@RestController
@RequestMapping("/products&services")
class ProductsServicesController
    @Autowired constructor(
        private val productsService: ProductsService,
        private val appointmentService: AppointmentService,
        private val servicesService: ServicesService
    )  {

    @GetMapping("")
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

    @GetMapping("/appointments")
    suspend fun findAllAppointments(): ResponseEntity<List<AppointmentDTO>> {
        logger.info {" On sale controller - findAll appointments() "}

        val res = appointmentService.findAll().toList()
            .map { it.toDTO() }

        return ResponseEntity.ok(res)

    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: Long): ResponseEntity<OnSale> {

        logger.info { "On sale controller - findById(): $id" }

        val result: OnSale = try {
            val product = productsService.findById(id)
            product

        } catch (e: ProductNotFoundException) {
            try {
                val service = servicesService.findById(id)
                service

            } catch (e: ServiceNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)

            }
        }
        return ResponseEntity.ok(result)
    }

    @GetMapping("/appointments/{id}")
    suspend fun findAppointmentById(@PathVariable id: Long): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - findAppointmentById(): $id" }

        try {
            val entity = appointmentService.findById(id)
            val result = entity.toDTO()
            return ResponseEntity.ok(result)
        } catch (e: AppointmentNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: AppointmentNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }

    }

    @PostMapping("")
    suspend fun create(@Valid @RequestBody entityDto: ProductCreateDTO): ResponseEntity<ProductDTO> {
        logger.info { "On sale controller - product create()"}

        try {

            val rep = entityDto.validate().toModel()
            val res = productsService.save(rep).toDTO()

            return ResponseEntity.status(HttpStatus.CREATED).body(res)

        } catch (e: ProductBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PostMapping("")
    suspend fun create(@Valid @RequestBody entityDto: ServiceCreateDTO): ResponseEntity<ServiceDTO> {

        logger.info { "On sale controller - service create()"}

        try {

            val rep = entityDto.validate().toModel()
            val res = servicesService.save(rep).toDTO()

            return ResponseEntity.status(HttpStatus.CREATED).body(res)

        } catch (e: ServiceBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }

    }

    @PostMapping("/appointments")
    suspend fun create(@Valid @RequestBody entityDto: AppointmentCreateDTO): ResponseEntity<AppointmentDTO> {
        logger.info { "On sale controller - appointment create()"}

        try {

            val rep = entityDto.validate().toModel()
            val res = appointmentService.save(rep).toDTO()

            return ResponseEntity.status(HttpStatus.CREATED).body(res)

        } catch (e: ProductBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody serviceDTO: ServiceCreateDTO
    ): ResponseEntity<ServiceDTO> {

        logger.info { "On sale controller - service update() : $id" }

        try {

            val rep = serviceDTO.validate().toModel()
            val res = servicesService.update(rep).toDTO()

            return ResponseEntity.status(HttpStatus.OK).body(res)

        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: ServiceBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody productDTO: ProductCreateDTO
    ): ResponseEntity<ProductDTO> {

        logger.info { "On sale controller - product update() : $id" }

        try {

            val rep = productDTO.validate().toModel()
            val res = productsService.update(rep).toDTO()

            return ResponseEntity.status(HttpStatus.OK).body(res)

        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: ServiceBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PutMapping("/appointments/{id}")
    suspend fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody entityDTO: AppointmentCreateDTO
    ): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - appointment update() : $id" }

        try {

            val rep = entityDTO.validate().toModel()
            val res = appointmentService.update(rep).toDTO()

            return ResponseEntity.status(HttpStatus.OK).body(res)

        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: ServiceNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: ServiceBadRequestException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }


    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<OnSaleDTO> {

        logger.info { "On sale controller - delete() : $id" }

        val result: OnSaleDTO = try {
            val product = productsService.delete(productsService.findById(id)).toDTO().toOnSaleDTO()
            product

        } catch (e: ProductNotFoundException) {
            try {
                val service = servicesService.delete(servicesService.findById(id)).toDTO().toOnSaleDTO()
                service

            } catch (e: ServiceNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)

            }
        }

        return ResponseEntity.ok(result)

    }

    @DeleteMapping("/appointments/{id}")
    suspend fun deleteAppointment(@PathVariable id: Long): ResponseEntity<AppointmentDTO> {

        logger.info { "On sale controller - deleteAppointment() : $id" }

        try {
            appointmentService.delete(appointmentService.findById(id))
            return ResponseEntity.noContent().build()
        } catch (e: AppointmentNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: AppointmentConflictIntegrityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }

    }
}