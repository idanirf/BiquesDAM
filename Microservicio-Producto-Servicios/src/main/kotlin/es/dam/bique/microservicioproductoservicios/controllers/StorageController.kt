package es.dam.bique.microservicioproductoservicios.controllers

import es.dam.bique.microservicioproductoservicios.exceptions.StorageBadRequestException
import es.dam.bique.microservicioproductoservicios.exceptions.StorageException
import es.dam.bique.microservicioproductoservicios.services.storage.StorageService
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import kotlinx.coroutines.*
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/products&services/storage")
class StorageController
@Autowired constructor(
    private val storageService: StorageService
    ){
    @GetMapping(value = ["{filename:.+}"])
    @ResponseBody
    fun serverFile(@PathVariable filename: String?, request: HttpServletRequest): ResponseEntity<Resource> = runBlocking {

        logger.info { "Searching for file: $filename" }

        val coScope = CoroutineScope(Dispatchers.IO)

        val file: Resource =
            withContext(coScope.coroutineContext) { storageService.loadAsResource(filename.toString()) }

        var contentType: String? = try {
            request.servletContext.getMimeType(file.file.absolutePath)
        } catch (ex: IOException) {
            throw StorageBadRequestException("Incorrect file extension.", ex)
        }

        if (contentType == null) {
            contentType = "application/octet-stream"
        }

        return@runBlocking ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body<Resource?>(file)
    }

    @PostMapping(value = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestPart("file") file: MultipartFile): ResponseEntity<Map<String, String>> = runBlocking {

        logger.info { "Saving file: ${file.originalFilename}" }

        return@runBlocking try {

            if (!file.isEmpty) {

                val coScope = CoroutineScope(Dispatchers.IO)

                val fileStored = withContext(coScope.coroutineContext) {
                    storageService.store(file)
                }

                val urlStored = storageService.getUrl(fileStored)
                val response = mapOf("url" to urlStored, "name" to fileStored, "created_at" to LocalDateTime.now().toString())
                ResponseEntity.status(HttpStatus.CREATED).body(response)

            } else {
                throw StorageBadRequestException("Cannot save an empty file")
            }

        } catch (e: StorageException) {
            throw StorageBadRequestException(e.message.toString())
        }
    }

    @DeleteMapping(value = ["{filename:.+}"])
    @ResponseBody
    fun deleteFile(@PathVariable filename: String?): ResponseEntity<Resource> = runBlocking {

        logger.info { "Deleting file: $filename" }

        try {

            CoroutineScope(Dispatchers.IO).launch {
                storageService.delete(filename.toString())
            }.join()

            return@runBlocking ResponseEntity.ok().build()

        } catch (e: StorageException) {
            throw StorageBadRequestException(e.message.toString())
        }
    }

}
