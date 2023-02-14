package es.dam.biques.microserviciousuarios.repositories

import es.dam.biques.microserviciousuarios.models.Usuario
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuariosRepository: CrudRepository<Usuario, Long> {
    fun findByEmail(email: String): Usuario?

}