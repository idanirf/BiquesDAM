package es.dam.bique.microservicioproductoservicios.controllers

import es.dam.bique.microservicioproductoservicios.dto.AppointmentCreateDTO
import es.dam.bique.microservicioproductoservicios.dto.ProductCreateDTO
import es.dam.bique.microservicioproductoservicios.dto.ServiceCreateDTO
import es.dam.bique.microservicioproductoservicios.exceptions.AppointmentNotFoundException
import es.dam.bique.microservicioproductoservicios.exceptions.ProductNotFoundException
import es.dam.bique.microservicioproductoservicios.exceptions.ServiceNotFoundException
import es.dam.bique.microservicioproductoservicios.mappers.toDTO
import es.dam.bique.microservicioproductoservicios.mappers.toOnSaleCreateDTO
import es.dam.bique.microservicioproductoservicios.models.*
import es.dam.bique.microservicioproductoservicios.services.appointments.AppointmentService
import es.dam.bique.microservicioproductoservicios.services.products.ProductsService
import es.dam.bique.microservicioproductoservicios.services.services.ServicesService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*
import org.junit.jupiter.api.assertThrows

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class ProductsServicesControllerTest {

    private val product = Product(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d12"),
        image = "test",
        brand = "test",
        model = "test",
        description = "test",
        price = 0.0f,
        discountPercentage = 0.0f,
        stock = StockType.SOLDOUT,
        isAvailable = false,
        type = ProductType.ACCESORIES
    )

    private val appointment = Appointment(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d14"),
        userId = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d13"),
        assistance = AssistanceType.ANY,
        date = LocalDate.parse("2023-03-03"),
        description = "test"
    )

    private val service = Service(
        id = 1L,
        uuid = UUID.fromString("e2613745-ad51-460f-8aa2-733379be7d15"),
        image = "test",
        price = 0.0f,
        appointment = appointment.uuid,
        type = ServiceType.REVISION
    )

    @MockK
    private lateinit var productService: ProductsService

    @MockK
    private lateinit var appointmentService: AppointmentService

    @MockK
    private lateinit var serviceService: ServicesService

    @InjectMockKs
    lateinit var controller: ProductsServicesController

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun findAll() = runTest {
        coEvery { productService.findAll() } returns flowOf(product)
        coEvery { serviceService.findAll() } returns flowOf(service)
        coEvery { serviceService.findAppointment(any()) } returns appointment

        val result = controller.findAll()
        val res = result.body?.data

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
            { assertEquals(2, res?.size) },

            { assertEquals(product.toDTO().id, res?.get(0)?.productEntity?.id) },
            { assertEquals(product.toDTO().uuid, res?.get(0)?.productEntity?.uuid) },
            { assertEquals(product.toDTO().image, res?.get(0)?.productEntity?.image) },
            { assertEquals(product.toDTO().brand, res?.get(0)?.productEntity?.brand) },
            { assertEquals(product.toDTO().model, res?.get(0)?.productEntity?.model) },
            { assertEquals(product.toDTO().description, res?.get(0)?.productEntity?.description) },
            { assertEquals(product.toDTO().price, res?.get(0)?.productEntity?.price) },
            { assertEquals(product.toDTO().discountPercentage, res?.get(0)?.productEntity?.discountPercentage) },
            { assertEquals(product.toDTO().stock, res?.get(0)?.productEntity?.stock) },
            { assertEquals(product.toDTO().isAvailable, res?.get(0)?.productEntity?.isAvailable) },
            { assertEquals(product.toDTO().type, res?.get(0)?.productEntity?.type) },

            { assertEquals(service.toDTO().id, res?.get(1)?.serviceEntity?.id) },
            { assertEquals(service.toDTO().uuid, res?.get(1)?.serviceEntity?.uuid) },
            { assertEquals(service.toDTO().image, res?.get(1)?.serviceEntity?.image) },
            { assertEquals(service.toDTO().price, res?.get(1)?.serviceEntity?.price) },
            { assertEquals(service.toDTO().appointment, res?.get(1)?.serviceEntity?.appointment) },
            { assertEquals(service.toDTO().type, res?.get(1)?.serviceEntity?.type) }
        )

        coVerify { productService.findAll() }
        coVerify { serviceService.findAll() }
    }

    @Test
    fun findAllAppointments() = runTest {
        coEvery { appointmentService.findAll() } returns flowOf(appointment)

        val result = controller.findAllAppointments()
        val res = result.body

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
            { assertEquals(1, res?.size) },

            { assertEquals(appointment.toDTO().id, res?.get(0)?.id) },
            { assertEquals(appointment.toDTO().uuid, res?.get(0)?.uuid) },
            { assertEquals(appointment.toDTO().user, res?.get(0)?.user) },
            { assertEquals(appointment.toDTO().assistance, res?.get(0)?.assistance) },
            { assertEquals(appointment.toDTO().date, res?.get(0)?.date) },
            { assertEquals(appointment.toDTO().description, res?.get(0)?.description) }
        )

        coVerify { appointmentService.findAll() }
    }

    @Test
    fun findById() = runTest {
        coEvery { productService.findById(any()) } returns product
        coEvery { serviceService.findById(any()) } returns service

        val result = controller.findById(product.id!!)
        val res = result.body?.data

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
            { assertEquals(2, res?.size) },

            { assertEquals(product.toDTO().id, res?.get(0)?.productEntity?.id) },
            { assertEquals(product.toDTO().uuid, res?.get(0)?.productEntity?.uuid) },
            { assertEquals(product.toDTO().image, res?.get(0)?.productEntity?.image) },
            { assertEquals(product.toDTO().brand, res?.get(0)?.productEntity?.brand) },
            { assertEquals(product.toDTO().model, res?.get(0)?.productEntity?.model) },
            { assertEquals(product.toDTO().description, res?.get(0)?.productEntity?.description) },
            { assertEquals(product.toDTO().price, res?.get(0)?.productEntity?.price) },
            { assertEquals(product.toDTO().discountPercentage, res?.get(0)?.productEntity?.discountPercentage) },
            { assertEquals(product.toDTO().stock, res?.get(0)?.productEntity?.stock) },
            { assertEquals(product.toDTO().isAvailable, res?.get(0)?.productEntity?.isAvailable) },
            { assertEquals(product.toDTO().type, res?.get(0)?.productEntity?.type) },

            { assertEquals(service.toDTO().id, res?.get(1)?.serviceEntity?.id) },
            { assertEquals(service.toDTO().uuid, res?.get(1)?.serviceEntity?.uuid) },
            { assertEquals(service.toDTO().image, res?.get(1)?.serviceEntity?.image) },
            { assertEquals(service.toDTO().price, res?.get(1)?.serviceEntity?.price) },
            { assertEquals(service.toDTO().appointment, res?.get(1)?.serviceEntity?.appointment) },
            { assertEquals(service.toDTO().type, res?.get(1)?.serviceEntity?.type) }
        )

        coVerify { productService.findById(any()) }
        coVerify { serviceService.findById(any()) }
    }

    @Test
    fun findByIdNoExiste() = runTest {
        coEvery { productService.findById(any()) } throws ProductNotFoundException("Not found with id: ${product.id}")
        coEvery { serviceService.findById(any()) } throws ServiceNotFoundException("Not found with id: ${service.id}")

        val res = assertThrows<ResponseStatusException> {
           controller.findById(1L)
        }

        assertEquals("""404 NOT_FOUND "Nothing found for id: ${product.id}"""", res.message)

        coVerify { productService.findById(any()) }
        coVerify { serviceService.findById(any()) }

    }

    @Test
    fun findAppointmentById()= runTest {
        coEvery { appointmentService.findById(any()) } returns appointment

        val result = controller.findAppointmentById(appointment.id!!)
        val res = result.body

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },

            { assertEquals(appointment.toDTO().id, res?.id) },
            { assertEquals(appointment.toDTO().uuid, res?.uuid) },
            { assertEquals(appointment.toDTO().user, res?.user) },
            { assertEquals(appointment.toDTO().assistance, res?.assistance) },
            { assertEquals(appointment.toDTO().date, res?.date) },
            { assertEquals(appointment.toDTO().description, res?.description) }
        )

        coVerify { appointmentService.findById(any()) }
    }

    @Test
    fun findAppointmentByIdNoExiste() = runTest {
        coEvery { appointmentService.findById(any()) } throws AppointmentNotFoundException("Appointment not found with id: ${appointment.id}")

        val res = assertThrows<ResponseStatusException> {
            controller.findAppointmentById(1L)
        }

        assertEquals("""404 NOT_FOUND "Appointment not found with id: ${appointment.id}"""", res.message)

        coVerify { appointmentService.findById(any()) }
    }

    @Test
    fun createProduct() = runTest {
        coEvery { productService.save(any()) } returns product

        val result = controller.create(
            ProductCreateDTO(
                image = "creado",
                brand = "creado",
                model = "creado",
                description = "creado",
                price = 0.1f,
                discountPercentage = 0.1f,
                stock = StockType.SOLDOUT.value,
                isAvailable = false,
                type = ProductType.ACCESORIES.value,
            ).toOnSaleCreateDTO()
        )


        val res = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.CREATED) },
            { assertEquals(product.toDTO().id, res.productEntity?.id) },
            { assertEquals(product.toDTO().uuid, res.productEntity?.uuid) },
            { assertEquals(product.toDTO().image, res.productEntity?.image) },
            { assertEquals(product.toDTO().brand, res.productEntity?.brand) },
            { assertEquals(product.toDTO().model, res.productEntity?.model) },
            { assertEquals(product.toDTO().description, res.productEntity?.description) },
            { assertEquals(product.toDTO().price, res.productEntity?.price) },
            { assertEquals(product.toDTO().discountPercentage, res.productEntity?.discountPercentage) },
            { assertEquals(product.toDTO().stock, res.productEntity?.stock) },
            { assertEquals(product.toDTO().isAvailable, res.productEntity?.isAvailable) },
            { assertEquals(product.toDTO().type, res.productEntity?.type) }
        )
    }

    @Test
    fun createService() = runTest {
        coEvery { serviceService.save(any()) } returns service

        val result = controller.create(
            ServiceCreateDTO(
                image = "create",
                price = 0.1f,
                appointment = appointment.uuid.toString(),
                type = ServiceType.REVISION.value
            ).toOnSaleCreateDTO()
        )

        val res = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.CREATED) },
            { assertEquals(service.toDTO().id, res.serviceEntity?.id) },
            { assertEquals(service.toDTO().uuid, res.serviceEntity?.uuid) },
            { assertEquals(service.toDTO().image, res.serviceEntity?.image) },
            { assertEquals(service.toDTO().price, res.serviceEntity?.price) },
            { assertEquals(service.toDTO().appointment, res.serviceEntity?.appointment) },
            { assertEquals(service.toDTO().type, res.serviceEntity?.type) }
        )
    }

    @Test
    fun createAppointment() = runTest {
        coEvery { appointmentService.save(any()) } returns appointment

        val result = controller.create(
            AppointmentCreateDTO(
                userId = "e2613745-ad51-460f-8aa2-733379be7d13",
                assistance = AssistanceType.ANY.value,
                date = "2023-03-03",
                description = "create"
            )
        )

        val res = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.CREATED) },
            { assertEquals(appointment.toDTO().id, res.id) },
            { assertEquals(appointment.toDTO().uuid, res.uuid) },
            { assertEquals(appointment.toDTO().user, res.user) },
            { assertEquals(appointment.toDTO().assistance, res.assistance) },
            { assertEquals(appointment.toDTO().date, res.date) },
            { assertEquals(appointment.toDTO().description, res.description) }
        )
    }

    @Test
    fun updateProduct()  = runTest {
        coEvery { productService.findByUuid(any()) } returns product
        coEvery { serviceService.findByUuid(any()) } returns service
        coEvery { productService.update(any()) } returns product
        coEvery { serviceService.update(any()) } returns service
        coEvery { serviceService.findAppointment(any()) } returns appointment

        val result = controller.update(
            product.uuid,
            ProductCreateDTO(
                image = "actualizado",
                brand = "actualizado",
                model = "actualizado",
                description = "actualizado",
                price = 0.1f,
                discountPercentage = 0.1f,
                stock = StockType.SOLDOUT.value,
                isAvailable = false,
                type = ProductType.ACCESORIES.value,
            ).toOnSaleCreateDTO()
        )

        val res = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
            { assertEquals(product.toDTO().id, res.productEntity?.id) },
            { assertEquals(product.toDTO().uuid, res.productEntity?.uuid) },
            { assertEquals(product.toDTO().image, res.productEntity?.image) },
            { assertEquals(product.toDTO().brand, res.productEntity?.brand) },
            { assertEquals(product.toDTO().model, res.productEntity?.model) },
            { assertEquals(product.toDTO().description, res.productEntity?.description) },
            { assertEquals(product.toDTO().price, res.productEntity?.price) },
            { assertEquals(product.toDTO().discountPercentage, res.productEntity?.discountPercentage) },
            { assertEquals(product.toDTO().stock, res.productEntity?.stock) },
            { assertEquals(product.toDTO().isAvailable, res.productEntity?.isAvailable) },
            { assertEquals(product.toDTO().type, res.productEntity?.type) }
        )
    }

    @Test
    fun updateService()  = runTest {
        coEvery { productService.findByUuid(any()) } returns product
        coEvery { serviceService.findByUuid(any()) } returns service
        coEvery { productService.update(any()) } returns product
        coEvery { serviceService.update(any()) } returns service
        coEvery { serviceService.findAppointment(any()) } returns appointment

        val result = controller.update(
            product.uuid,
            ServiceCreateDTO(
                image = "actualizado",
                price = 0.1f,
                appointment = appointment.uuid.toString(),
                type = ServiceType.REVISION.value
            ).toOnSaleCreateDTO()
        )

        val res = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
            { assertEquals(service.toDTO().id, res.serviceEntity?.id) },
            { assertEquals(service.toDTO().uuid, res.serviceEntity?.uuid) },
            { assertEquals(service.toDTO().image, res.serviceEntity?.image) },
            { assertEquals(service.toDTO().price, res.serviceEntity?.price) },
            { assertEquals(service.toDTO().appointment, res.serviceEntity?.appointment) },
            { assertEquals(service.toDTO().type, res.serviceEntity?.type) }
        )
    }

    @Test
    fun updateAppointment() = runTest {
        coEvery { appointmentService.update(any()) } returns appointment

        val result = controller.update(
            appointment.uuid,
            AppointmentCreateDTO(
                userId = "e2613745-ad51-460f-8aa2-733379be7d13",
                assistance = AssistanceType.ANY.value,
                date = "2023-03-03",
                description = "update"
            )
        )

        val res = result.body!!

        assertAll(
            { assertNotNull(result) },
            { assertNotNull(res) },
            { assertEquals(result.statusCode, HttpStatus.OK) },
            { assertEquals(appointment.toDTO().id, res.id) },
            { assertEquals(appointment.toDTO().uuid, res.uuid) },
            { assertEquals(appointment.toDTO().user, res.user) },
            { assertEquals(appointment.toDTO().assistance, res.assistance) },
            { assertEquals(appointment.toDTO().date, res.date) },
            { assertEquals(appointment.toDTO().description, res.description) }
        )
    }

    @Test
    fun updateNoExiste() = runTest {
        coEvery { productService.findByUuid(any()) } throws ProductNotFoundException("Not found with uuid: ${product.uuid}")
        coEvery { serviceService.findByUuid(any()) } throws ServiceNotFoundException("Not found with uuid: ${service.uuid}")
        coEvery { productService.update(any()) } throws ProductNotFoundException("Not found with uuid: ${product.uuid}")
        coEvery { serviceService.update(any()) } throws ServiceNotFoundException("Not found with uuid: ${service.uuid}")
        coEvery { serviceService.findAppointment(any()) } returns appointment


        val res = assertThrows<ResponseStatusException> {
            val result = controller.update(
                product.uuid,
                ProductCreateDTO(
                    image = "actualizado",
                    brand = "actualizado",
                    model = "actualizado",
                    description = "actualizado",
                    price = 0.1f,
                    discountPercentage = 0.1f,
                    stock = StockType.SOLDOUT.value,
                    isAvailable = false,
                    type = ProductType.ACCESORIES.value
                ).toOnSaleCreateDTO()
            )
        }


        assertEquals("""404 NOT_FOUND "Not found with uuid: ${product.uuid}"""", res.message)
    }

    @Test
    fun updateAppointmentNoExiste() = runTest {
        coEvery { appointmentService.update(any()) } throws AppointmentNotFoundException("Appointment not found with id: ${appointment.uuid}")

        val res = assertThrows<AppointmentNotFoundException> {
            controller.update(
                appointment.uuid,
                AppointmentCreateDTO(
                    userId = "e2613745-ad51-460f-8aa2-733379be7d13",
                    assistance = AssistanceType.ANY.value,
                    date = "2023-03-03",
                    description = "update"
                )
            )
        }

        assertEquals("Appointment not found with id: ${appointment.uuid}", res.message)
    }

    @Test
    fun deleteProduct() = runTest {
        coEvery { productService.findByUuid(any()) } returns product
        coEvery { productService.delete(any()) } returns product

        val result = controller.delete(product.uuid)

        assertAll(
            { assertNotNull(result) },
            { assertEquals(result.statusCode, HttpStatus.NO_CONTENT) },
        )
    }

    @Test
    fun deleteService() = runTest {
        coEvery { productService.findByUuid(any()) } returns product
        coEvery { serviceService.findByUuid(any()) } returns service
        coEvery { productService.delete(any()) } returns product
        coEvery { serviceService.delete(any()) } returns service

        val result = controller.delete(service.uuid)

        assertAll(
            { assertNotNull(result) },
            { assertEquals(result.statusCode, HttpStatus.NO_CONTENT) },
        )
    }

    @Test
    fun deleteAppointment() = runTest {
        coEvery { productService.findByUuid(any()) } returns product
        coEvery { serviceService.findByUuid(any()) } returns service
        coEvery { appointmentService.findByUuid(any()) } returns appointment
        coEvery { productService.delete(any()) } returns product
        coEvery { appointmentService.delete(any()) } returns appointment

        val result = controller.delete(appointment.uuid)

        assertAll(
            { assertNotNull(result) },
            { assertEquals(result.statusCode, HttpStatus.NO_CONTENT) },
        )
    }

    @Test
    fun deleteProductNoExiste() = runTest {
        coEvery { serviceService.findByUuid(any()) } throws ProductNotFoundException("Not found with id: ${product.uuid}")
        coEvery { productService.findByUuid(any()) } throws ProductNotFoundException("Not found with id: ${product.uuid}")
        coEvery { productService.delete(any()) } throws ProductNotFoundException("Not found with id: ${product.uuid}")

        val res = assertThrows<ProductNotFoundException> {
            controller.delete(product.uuid)
        }

        assertEquals(
            "Not found with id: ${product.uuid}",
            res.message
        )

    }

    @Test
    fun deleteServiceNoExiste() = runTest {
        coEvery { productService.findByUuid(any()) } throws ProductNotFoundException("Not found with id: ${product.uuid}")
        coEvery { serviceService.findByUuid(any()) } throws ServiceNotFoundException("Not found with id: ${service.uuid}")
        coEvery { serviceService.delete(any()) } throws ServiceNotFoundException("Not found with id: ${service.uuid}")

        val res = assertThrows<ResponseStatusException> {
            val result = controller.delete(service.uuid)
        }

        assertEquals(
            """404 NOT_FOUND "Not found with id: ${service.uuid}"""",
            res.message
        )
    }

    @Test
    fun deleteAppointmentNoExiste() = runTest {
        coEvery { productService.findByUuid(any()) } throws ProductNotFoundException("Not found with id: ${appointment.uuid}")
        coEvery { serviceService.findByUuid(any()) } throws ServiceNotFoundException("Not found with id: ${appointment.uuid}")
        coEvery { appointmentService.findByUuid(any()) } throws AppointmentNotFoundException("Appointment not found with id: ${appointment.uuid}")
        coEvery { appointmentService.delete(any()) } throws AppointmentNotFoundException("Appointment not found with id: ${appointment.uuid}")

        val res = assertThrows<ResponseStatusException> {
            controller.delete(service.uuid)
        }

        assertEquals("""404 NOT_FOUND "Not found with id: ${appointment.uuid}"""", res.message)

    }
}