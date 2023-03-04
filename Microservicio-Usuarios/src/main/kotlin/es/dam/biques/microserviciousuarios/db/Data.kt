package es.dam.biques.microserviciousuarios.db

import es.dam.biques.microserviciousuarios.models.User

fun getUsersInit() = listOf(
    User(
        username = "alejandro",
        email = "alejandro@correo.com",
        password = "alejandro1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Aranjuez",
        rol = User.TipoUsuario.ADMIN.name
    ),
    User(
        username = "jorge",
        email = "jorge@correo.com",
        password = "jorge1234",
        image = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        address = "Leganés",
        rol = User.TipoUsuario.CLIENT.name
    )
)