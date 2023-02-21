package es.dam.biques.microserviciousuarios.db

import es.dam.biques.microserviciousuarios.models.User
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.*

fun getUsersInit() = listOf(
    User(
        uuid = UUID.fromString("b39a2fd2-f7d7-405d-b73c-b68a8dedbcdf"),
        username = "pepe",
        email = "pepe@perez.com",
        password = BCrypt.hashpw("pepe1234", BCrypt.gensalt(12)),
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Pepe Services",
        type = User.TipoUsuario.ADMIN.name
    ),
    User(
        uuid = UUID.fromString("c53062e4-31ea-4f5e-a99d-36c228ed01a3"),
        username = "ana",
        email = "ana@lopez.com",
        password = BCrypt.hashpw("ana1234", BCrypt.gensalt(12)),
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Juan Services",
        type = User.TipoUsuario.CLIENT.name
    )
)