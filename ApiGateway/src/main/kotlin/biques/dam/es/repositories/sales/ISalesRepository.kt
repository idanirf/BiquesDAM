package biques.dam.es.repositories.sales

import biques.dam.es.dto.SaleCreateDTO
import biques.dam.es.dto.SaleDTO
import java.util.*

interface ISalesRepository {
    suspend fun findAll(token: String): List<SaleDTO>
    suspend fun findById(token: String, id: String): List<SaleDTO>
    suspend fun save(token: String, entity: SaleCreateDTO): SaleDTO
    suspend fun update(token: String, id: UUID, entity: SaleCreateDTO): SaleDTO
    suspend fun delete(token: String, id: UUID)
}

