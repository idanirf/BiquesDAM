package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.CRUDRepository

interface IAppointmentsCachedRepository : CRUDRepository<Appointment, Long> {
}