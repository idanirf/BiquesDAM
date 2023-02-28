package es.dam.bique.microservicioproductoservicios.services.storage

import es.dam.bique.microservicioproductoservicios.controllers.StorageController
import es.dam.bique.microservicioproductoservicios.exceptions.StorageBadRequestException
import es.dam.bique.microservicioproductoservicios.exceptions.StorageFileNotFoundException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Stream


private val logger = KotlinLogging.logger {}

/*@Service
class StorageService(
    @Value("\${upload.root-location}") path: String,
    @Value("\${spring.profiles.active}") mode: String

):IStorageService {

    private val rootLocation: Path

    init {

        logger.info { "File storage is starting" }
        rootLocation = Paths.get(path)
        if (mode == "admin") {
            this.deleteAll()
        }

    }


    override fun store(file: MultipartFile): String {

        logger.info { "Saving file: ${file.originalFilename}" }

        val filename = StringUtils.cleanPath(file.originalFilename.toString())
        val extension = StringUtils.getFilenameExtension(filename).toString()
        //val justFilename = filename.replace(".$extension", "")
        val storedFilename = UUID.randomUUID().toString() + "." + extension
        try {
            if (file.isEmpty) {
                throw StorageBadRequestException("Empty file $filename")
            }
            if (filename.contains("..")) {
                throw StorageBadRequestException("Non authorised path $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, rootLocation.resolve(storedFilename),
                    StandardCopyOption.REPLACE_EXISTING
                )

                return storedFilename

            }
        } catch (e: IOException) {
            throw StorageBadRequestException("Error while saving file: $filename", e)
        }
    }


    override fun store(file: MultipartFile, filenameFromUser: String): String {

        logger.info { "Saving file: ${file.originalFilename}" }

        val filename = StringUtils.cleanPath(file.originalFilename.toString())
        val extension = StringUtils.getFilenameExtension(filename).toString()
        val storedFilename = "$filenameFromUser.$extension"
        try {
            if (file.isEmpty) {
                throw StorageBadRequestException("Empty file $filename")
            }
            if (filename.contains("..")) {
                throw StorageBadRequestException("Non authorised path $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, rootLocation.resolve(storedFilename),
                    StandardCopyOption.REPLACE_EXISTING
                )

                return storedFilename

            }
        } catch (e: IOException) {
            throw StorageBadRequestException("Error while saving file: $filename", e)
        }
    }

    override fun loadAll(): Stream<Path> {

        logger.info { "Loading all files" }

        return try {
            Files.walk(rootLocation, 1)
                .filter { path -> !path.equals(rootLocation) }
                .map(rootLocation::relativize)
        } catch (e: IOException) {
            throw StorageBadRequestException("Error while reading stored files", e)
        }
    }


    override fun load(filename: String): Path {

        logger.info { "Loading file: $filename" }

        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {

        logger.info { "Loading file as resource: $filename" }

        return try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                    "Cannot read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Cannot read file: $filename", e)
        }
    }

    override fun deleteAll() {

        logger.info { "Deleting all files" }

        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {

        logger.info { "Initializing storage file directories" }

        try {
            if (!Files.exists(rootLocation))
                Files.createDirectory(rootLocation)
        } catch (e: IOException) {
            throw StorageBadRequestException("Couldn't initialize the storage service", e)
        }
    }

    override fun delete(filename: String) {

        logger.info { "Deleting file: $filename" }

        val justFilename: String = StringUtils.getFilename(filename).toString()
        try {
            val file = load(justFilename)
            if (!Files.exists(file))
                 throw StorageFileNotFoundException("File $filename does not exist")
             else
                 Files.delete(file)
        } catch (e: IOException) {
            throw StorageBadRequestException("An error occurred while deleting a file", e)
        }

    }

    override fun getUrl(filename: String): String {
        logger.info { "Obtaining file: $filename" }

        return MvcUriComponentsBuilder
            .fromMethodName(StorageController::class.java, "serverFile", filename, null)
            .build().toUriString()
    }
}

 */