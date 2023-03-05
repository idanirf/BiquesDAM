package biques.dam.es.repositories.sales

import biques.dam.es.dto.SaleDTO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ISalesRepository{
    suspend fun findAll(token: String): Flow<SaleDTO>
    suspend fun findById(token: String, id: UUID): Flow<SaleDTO>
    suspend fun save(token: String, entity: SaleDTO): SaleDTO
    suspend fun update(token: String, id: UUID, entity: SaleDTO): SaleDTO
    suspend fun delete(token: String, id: UUID)
}

