package biques.dam.es.repositories.sales

import biques.dam.es.dto.SaleCreateDTO
import biques.dam.es.dto.SaleDTO
import java.util.*

/**
 * This interface represents the repository for the Sale model.
 * @author BiquesDAM-Team
 */
interface ISalesRepository {
    /**
     * Find all sales
     * @param token JWT token
     * @return List of SalesDTO
     */
    suspend fun findAll(token: String): List<SaleDTO>

    /**
     * Find sale by id
     * @param token JWT token
     * @param id Sale String
     * @return SaleDTO
     */
    suspend fun findById(token: String, id: String): List<SaleDTO>
    /**
     * Create a new sale
     * @param token JWT token
     * @param dto SaleCreateDTO
     * @return SaleDTO
     */
    suspend fun save(token: String, entity: SaleCreateDTO): SaleDTO
    /**
     * Update an sale
     * @param token JWT token
     * @param id Sale uuid
     * @param dto SaleCreateDTO
     * @return SaleDTO
     */
    suspend fun update(token: String, id: UUID, entity: SaleCreateDTO): SaleDTO
    /**
     * Delete an sale
     * @param token JWT String
     * @param id Sale uuid
     */
    suspend fun delete(token: String, id: UUID)
}

