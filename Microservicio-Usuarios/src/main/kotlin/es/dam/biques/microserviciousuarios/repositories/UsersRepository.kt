package es.dam.biques.microserviciousuarios.repositories

<<<<<<< HEAD:Microservicio-Usuarios/src/main/kotlin/es/dam/biques/microserviciousuarios/repositories/UsuariosRepository.kt
import es.dam.biques.microserviciousuarios.models.Usuario
import kotlinx.coroutines.flow.Flow
=======
import es.dam.biques.microserviciousuarios.models.User
>>>>>>> 492d645d4e1853183c21fbe6710aab4c1b09fb8b:Microservicio-Usuarios/src/main/kotlin/es/dam/biques/microserviciousuarios/repositories/UsersRepository.kt
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
<<<<<<< HEAD:Microservicio-Usuarios/src/main/kotlin/es/dam/biques/microserviciousuarios/repositories/UsuariosRepository.kt
interface UsuariosRepository: CrudRepository<Usuario, Long> {
    fun findByEmail(email: String): Usuario?

=======
interface UsersRepository : CrudRepository<User, Long> {
    fun findUsersByEmail(email: String): User?
>>>>>>> 492d645d4e1853183c21fbe6710aab4c1b09fb8b:Microservicio-Usuarios/src/main/kotlin/es/dam/biques/microserviciousuarios/repositories/UsersRepository.kt
}