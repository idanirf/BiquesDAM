package biques.dam.es.repositories.orderline

import biques.dam.es.models.OrderLine
import biques.dam.es.repositories.CrudRepository
import org.litote.kmongo.Id

interface OrderLineRepository: CrudRepository<OrderLine, Id<OrderLine>> {


}