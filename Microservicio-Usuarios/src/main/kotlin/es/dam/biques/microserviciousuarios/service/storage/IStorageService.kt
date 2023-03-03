package es.dam.biques.microserviciousuarios.service.storage

import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream

interface IStorageService {
    fun initStorageService()
    fun loadAll(): Stream<Path>
    fun loadFile(fileName: String): Path
    fun storeFile(file: MultipartFile): String
    fun deleteFile(fileName: String)
}