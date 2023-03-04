package es.dam.biques.microserviciousuarios.controllers

import es.dam.biques.microserviciousuarios.exceptions.StorageBadRequestException
import es.dam.biques.microserviciousuarios.exceptions.StorageException
import es.dam.biques.microserviciousuarios.exceptions.StorageNotFoundException
import es.dam.biques.microserviciousuarios.service.storage.StorageService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.io.IOException
import java.time.LocalDateTime

@RestController
@RequestMapping("/users/storage")
class StorageController
@Autowired constructor(
    private val storageService: StorageService
) {
    @GetMapping(value = ["{filename:.+}"])
    @ResponseBody
    fun load(@PathVariable filename: String?, request: HttpServletRequest): ResponseEntity<Resource> =
        runBlocking {
            try {
                val scope = CoroutineScope(Dispatchers.IO)

                val file = storageService.loadFile(filename!!)

                val fileResource: Resource = scope.async {
                    UrlResource(file.toUri())
                }.await()

                var contentType = try {
                    request.servletContext.getMimeType(fileResource.file.absolutePath)
                } catch (e: IOException) {
                    throw StorageBadRequestException("Can not determinate content type -> ${e.message}")
                }

                if (contentType == null) {
                    contentType = "application/octet-stream"
                }

                return@runBlocking ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body<Resource?>(fileResource)
            } catch (e: Exception) {
                throw StorageNotFoundException("Unable action -> ${e.message}")
            }
        }

    @PostMapping(value = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") file: MultipartFile): ResponseEntity<Map<String, String>> = runBlocking {
        return@runBlocking try {
            if (!file.isEmpty) {
                val scope = CoroutineScope(Dispatchers.IO)
                val stored = scope.async { storageService.storeFile(file) }.await()

                val url = MvcUriComponentsBuilder
                    .fromMethodName(StorageController::class.java, "load", stored, null)
                    .build().toUriString()

                val response =
                    mapOf("url" to url, "name" to stored, "created_at" to LocalDateTime.now().toString())
                ResponseEntity.status(HttpStatus.CREATED).body(response)
            } else {
                throw StorageBadRequestException("Can not load file.")
            }

        } catch (e: StorageException) {
            throw StorageBadRequestException(e.message.toString())
        }
    }

    @DeleteMapping(value = ["{filename:.+}"])
    @ResponseBody
    fun delete(@PathVariable filename: String?, request: HttpServletRequest): ResponseEntity<Resource> = runBlocking {
        try {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                storageService.deleteFile(filename!!)
            }.join()

            return@runBlocking ResponseEntity.ok().build()
        } catch (e: StorageException) {
            throw StorageBadRequestException(e.message.toString())
        }
    }
}