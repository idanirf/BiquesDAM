package es.dam.bique.microservicioproductoservicios.repositories.appointments

import es.dam.bique.microservicioproductoservicios.models.Appointment
import es.dam.bique.microservicioproductoservicios.repositories.CRUDRepository

/**
 * Interface to define the methods to access the appointments cached repository.
 * @author The BiquesDAM Team
 */
interface IAppointmentsCachedRepository : CRUDRepository<Appointment, Long>