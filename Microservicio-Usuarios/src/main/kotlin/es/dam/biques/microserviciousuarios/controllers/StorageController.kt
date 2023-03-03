package es.dam.biques.microserviciousuarios.controllers

import es.dam.biques.microserviciousuarios.service.storage.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/storage")
class StorageController
@Autowired constructor(
    private val storageService: StorageService
) {
    
}