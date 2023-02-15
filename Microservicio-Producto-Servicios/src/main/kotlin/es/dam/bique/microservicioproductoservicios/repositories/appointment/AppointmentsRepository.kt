package es.dam.bique.microservicioproductoservicios.repositories.appointment

import es.dam.bique.microservicioproductoservicios.models.Appointment
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentsRepository : CoroutineCrudRepository<Appointment, Long> {
}