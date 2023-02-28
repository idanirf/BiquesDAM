package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.CRUDRepository
import org.springframework.stereotype.Repository

interface IAppointmentsCachedRepository : CRUDRepository<Appointment, Long> {
}