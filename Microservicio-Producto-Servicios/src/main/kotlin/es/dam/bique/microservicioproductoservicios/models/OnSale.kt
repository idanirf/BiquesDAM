package es.dam.bique.microservicioproductoservicios.models

import java.util.*

/**
 * Represents the common properties of the entities.
 * @param id Entity identifier.
 * @param uuid Entity unique identifier.
 * @author The BiquesDam Team.
 */
interface OnSale {
    val id: Long?
    val uuid: UUID
}