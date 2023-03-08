package es.dam.biques.microserviciousuarios.service.storage

import es.dam.biques.microserviciousuarios.exceptions.StorageBadRequestException
import es.dam.biques.microserviciousuarios.exceptions.StorageNotFoundException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Stream

private val logger = KotlinLogging.logger {}

@Service
class StorageService(
    @Value("\${upload.root-location}") path: String,
) : IStorageService {
    private val ruta: Path

    init {
        logger.info { "Initializing service." }

        ruta = Paths.get(path)
        this.initStorageService()
    }

    /**
     * Initializes the storage service.
     * @throws StorageBadRequestException If an error occurs while initializing the storage service.
     * @author BiquesDAM-Team
     */
    override fun initStorageService() {
        try {
            if (!Files.exists(ruta))
                Files.createDirectory(ruta)
        } catch (e: IOException) {
            throw StorageBadRequestException("Unable to initialize storage service -> ${e.message}")
        }
    }

    /**
     * Loads all the files from the storage service.
     * @return A stream of the files in the storage service.
     * @throws StorageNotFoundException If the storage service is empty.
     * @author BiquesDAM-Team
     */
    override fun loadAll(): Stream<Path> {
        logger.info { "Loading storage..." }

        return try {
            Files.walk(ruta, 1)
                .filter { path -> !path.equals(ruta) }
                .map(ruta::relativize)
        } catch (e: IOException) {
            throw StorageBadRequestException("Error reading storage service -> ${e.message}")
        }
    }

    /**
     * Loads a file with the given [fileName] from the storage service.
     * @param fileName The name of the file to be loaded.
     * @return The file with the given [fileName].
     * @throws StorageNotFoundException If the file is not found in the storage service.
     * @author BiquesDAM-Team
     */
    override fun loadFile(fileName: String): Path {
        logger.info { "Loading $fileName..." }

        return ruta.resolve(fileName)
    }

    /**
     * Stores the provided multipart file in the file system.
     * @param file The multipart file to store.
     * @return The filename under which the file was stored.
     * @throws StorageBadRequestException If an error occurs while storing the file.
     * @author BiquesDAM-Team
     */
    override fun storeFile(file: MultipartFile): String {
        logger.info { "Storing $file..." }

        val fileName = StringUtils.cleanPath(file.originalFilename.toString())
        val extension = StringUtils.getFilenameExtension(fileName).toString()
        val saved = UUID.randomUUID().toString() + "." + extension

        try {
            if (file.isEmpty) {
                throw StorageBadRequestException("Error storing: $fileName")
            }
            if (fileName.contains("..")) {
                throw StorageBadRequestException("Security path error storing: $fileName")
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, ruta.resolve(saved),
                    StandardCopyOption.REPLACE_EXISTING
                )
                return saved
            }
        } catch (e: IOException) {
            throw StorageBadRequestException("Error storing: $fileName -> ${e.message}")
        }
    }

    /**
     * Deletes a file with the given [fileName] from the storage service.
     * @param fileName The name of the file to be deleted.
     * @throws StorageNotFoundException If the file is not found in the storage service.
     * @throws StorageBadRequestException If there is an error deleting the file.
     * @author BiquesDAM-Team
     */
    override fun deleteFile(fileName: String) {
        logger.info { "Deleting $fileName..." }

        try {
            val file = loadFile(fileName)
            Files.deleteIfExists(file)

            if (!Files.exists(file)) {
                throw StorageNotFoundException("File $fileName not found.")
            } else {
                Files.delete(file)
            }

        } catch (e: IOException) {
            throw StorageBadRequestException("Error deleting: $fileName -> ${e.message}")
        }
    }
}