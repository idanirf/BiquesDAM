package es.dam.bique.microservicioproductoservicios.services.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream

interface IStorageService {

    fun init()
    fun store(file: MultipartFile): String
    fun loadAll(): Stream<Path>
    fun load(filename: String): Path
    fun loadAsResource(filename: String): Resource
    fun delete(filename: String)
    fun deleteAll()
    fun getUrl(filename: String): String
    fun store(file: MultipartFile, filenameFromUser: String): String

}