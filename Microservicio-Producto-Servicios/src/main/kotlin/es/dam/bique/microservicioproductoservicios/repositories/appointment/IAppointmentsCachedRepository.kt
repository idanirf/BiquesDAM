package es.dam.bique.microservicioproductoservicios.repositories.appointment

import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.CRUDRepository
import org.springframework.stereotype.Repository

@Repository
interface IAppointmentsCachedRepository : CRUDRepository<Appointment, Long> {
}