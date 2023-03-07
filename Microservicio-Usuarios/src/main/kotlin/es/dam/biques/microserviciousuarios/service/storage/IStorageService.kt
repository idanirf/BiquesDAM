package es.dam.biques.microserviciousuarios.service.storage

import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream

interface IStorageService {

    /**
     * Initializes the storage service.
     * This method may perform any necessary setup or configuration of the storage service
     * before it can be used by the application.
     * @throws StorageServiceException if there is an error during initialization
     * @author BiquesDAM-Team
     */
    fun initStorageService()

    /**
     * Loads all files stored in the storage service.
     * @return a Stream of Path objects representing all files stored in the storage service
     * @throws StorageServiceException if there is an error during the loading process
     * @author BiquesDAM-Team
     */
    fun loadAll(): Stream<Path>

    /**
     * Loads a file with the specified name from the storage service.
     * @param fileName the name of the file to be loaded
     * @return the Path object representing the loaded file
     * @throws FileNotFoundException if the specified file does not exist
     * @throws StorageServiceException if there is an error during the loading process
     * @author BiquesDAM-Team
     */
    fun loadFile(fileName: String): Path

    /**
     * Stores the specified file in the storage service.
     * @param file the file to be stored
     * @return the name of the stored file
     * @throws StorageServiceException if there is an error during the storage process
     * @author BiquesDAM-Team
     */
    fun storeFile(file: MultipartFile): String

    /**
     * Deletes a file with the specified name from the storage service.
     * @param fileName the name of the file to be deleted
     * @throws FileNotFoundException if the specified file does not exist
     * @throws StorageServiceException if there is an error during the deletion process
     * @author BiquesDAM-Team
     */
    fun deleteFile(fileName: String)
}