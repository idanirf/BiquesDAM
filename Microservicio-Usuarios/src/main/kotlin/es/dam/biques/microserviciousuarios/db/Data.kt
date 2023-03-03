package es.dam.biques.microserviciousuarios.db

import es.dam.biques.microserviciousuarios.models.User
import java.util.*

fun getUsersInit() = listOf(
    User(
        uuid = UUID.fromString("b39a2fd2-f7d7-405d-b73c-b68a8dedbcdf"),
        username = "alejandro",
        email = "alejandro@correo.com",
        password = "alejandro1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Aranjuez",
        rol = User.TipoUsuario.ADMIN.name
    ),
    User(
        uuid = UUID.fromString("c53062e4-31ea-4f5e-a99d-36c228ed01a3"),
        username = "jorge",
        email = "jorge@correo.com",
        password = "jorge1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Leganés",
        rol = User.TipoUsuario.CLIENT.name
    )
)