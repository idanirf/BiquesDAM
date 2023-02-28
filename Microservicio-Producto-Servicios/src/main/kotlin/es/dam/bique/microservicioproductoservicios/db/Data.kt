package es.dam.bique.microservicioproductoservicios.db

import es.dam.bique.microservicioproductoservicios.dto.ProductDTO

fun getProductsInit() = listOf(
    ProductDTO(
        1,
        "8ff7c820-c2d7-44a4-b8d7-80579c548977",
        "asdfg",
        "prueba",
        "prueba",
        "prueba",
        12.0f,
        0.0f,
        "LIMITED",
        false,
        "BIKES"
    )
)
