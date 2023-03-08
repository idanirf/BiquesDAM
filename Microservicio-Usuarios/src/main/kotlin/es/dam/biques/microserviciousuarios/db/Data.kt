package es.dam.biques.microserviciousuarios.db

import es.dam.biques.microserviciousuarios.models.User

/**
 * Retorna una lista de usuarios inicializados con valores predeterminados.
 * Los usuarios generados son:
 * suario administrador con username "alejandro", email "alejandro@correo.com", contraseña "alejandro1234", imagen de perfil predeterminada,
 * dirección "Aranjuez" y rol "ADMIN".
 * Usuario cliente con username "jorge", email "jorge@correo.com", contraseña "jorge1234", imagen de perfil predeterminada,
 * dirección "Leganés" y rol "CLIENT".
 * @return Lista de usuarios inicializados.
 * @author BiquesDAM-Team
 */
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