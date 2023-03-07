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

/**
 * Class to manage the storage of files
 * @param path Path where the files will be stored
 * @author The BiquesDAM Team
 */
@Service
class StorageService(
    @Value("\${upload.root-location}") path: String,
):IStorageService {

    private val rootLocation: Path = Paths.get(path)

    init {
        logger.info { "File storage is starting" }
        initStorageDirectory()
    }

    /**
     * Method to create the directory where the files will be stored
     * @author The BiquesDAM Team
     */
    private final fun initStorageDirectory() {
        if (!Files.exists(rootLocation)) {
            logger.info { "Creating storage directory: $rootLocation" }
            Files.createDirectory(rootLocation)
        } else {
            logger.info { "The directory for the storage already exists; data will be deleted" }
            deleteAll()
            Files.createDirectory(rootLocation)
        }
    }


    /**
     * Saves a MultipartFile in the storage
     * @param file MultipartFile to save
     * @return The name of the file stored
     * @throws StorageBadRequestException If the file is empty or the path is not authorised
     * @author The BiquesDAM Team
     */
    override fun store(file: MultipartFile): String {

        logger.info { "Saving file: ${file.originalFilename}" }

        val filename = StringUtils.cleanPath(file.originalFilename.toString())
        val extension = StringUtils.getFilenameExtension(filename).toString()
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

    /**
     * Saves a MultipartFile in the storage with the name provided by the user
     * @param file MultipartFile to save
     * @param filenameFromUser Name of the file to save
     * @return The name of the file stored
     * @throws StorageBadRequestException If the file is empty or the path is not authorised
     * @author The BiquesDAM Team
     */
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

    /**
     * Loads all the files stored in the storage
     * @return A Stream with the paths of the files stored
     * @throws StorageBadRequestException If there is an error while reading the files stored in the storage directory
     * @author The BiquesDAM Team
     */
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

    /**
     * Loads a file stored in the storage
     * @param filename Name of the file to load
     * @return The path of the file stored
     * @author The BiquesDAM Team
     */
    override fun load(filename: String): Path {

        logger.info { "Loading file: $filename" }

        return rootLocation.resolve(filename)
    }

    /**
     * Loads a file stored in the storage as a Resource
     * @param filename Name of the file to load
     * @return The Resource of the file stored
     * @throws StorageFileNotFoundException If the file is not found or it is not readable
     * @author The BiquesDAM Team
     */
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

    /**
     * Deletes all the files stored in the storage
     * @author The BiquesDAM Team
     */
    override fun deleteAll() {

        logger.info { "Deleting all files" }

        FileSystemUtils.deleteRecursively(rootLocation.toFile())

    }

    /**
     * Initializes the storage directory
     * @throws StorageBadRequestException If there is an error while creating the storage directory
     * @author The BiquesDAM Team
     */
    override fun init() {

        logger.info { "Initializing storage file directory" }

        try {
            if (!Files.exists(rootLocation))
                Files.createDirectory(rootLocation)
        } catch (e: IOException) {
            throw StorageBadRequestException("Couldn't initialize the storage service", e)
        }
    }

    /**
     * Deletes a file stored in the storage
     * @param filename Name of the file to delete
     * @throws StorageFileNotFoundException If the file is not found
     * @throws StorageBadRequestException If there is an error while deleting the file
     * @author The BiquesDAM Team
     */
    override fun delete(filename: String) {

        logger.info { "Deleting file: $filename" }

        val justFilename: String = StringUtils.getFilename(filename).toString()
        try {
            val file = load(justFilename)
            if (!Files.exists(file)) {
                throw StorageFileNotFoundException("File $filename does not exist")
            }else {
                Files.delete(file)
            }
        } catch (e: IOException) {
            throw StorageBadRequestException("An error occurred while deleting a file", e)
        }

    }

    /**
     * Obtains the URL of a file stored in the storage
     * @param filename Name of the file to obtain the URL
     * @return The URL of the file stored
     * @author The BiquesDAM Team
     */
    override fun getUrl(filename: String): String {
        logger.info { "Obtaining file: $filename" }

        return MvcUriComponentsBuilder
            .fromMethodName(StorageController::class.java, "serverFile", filename, null)
            .build().toUriString()
    }
}