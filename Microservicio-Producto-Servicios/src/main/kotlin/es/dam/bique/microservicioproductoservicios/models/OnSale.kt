package es.dam.bique.microservicioproductoservicios.models

import org.litote.kmongo.Id
import java.util.*

interface OnSale{
    val id: Id<Product>
    val uuid: UUID
}