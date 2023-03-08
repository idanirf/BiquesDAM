package es.dam.bique.microservicioproductoservicios.utils

import java.io.FileNotFoundException
import java.util.*

/**
 * Class to read properties from a file
 * @param fileName name of the file to read
 * @author The BiquesDAM Team
 */
class PropertiesReader(private val fileName: String) {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader.getResourceAsStream(fileName)
        if (file != null) {
            properties.load(file)
        } else {
            throw FileNotFoundException("File: $fileName not found")
        }
    }

    /**
     * Get a property from the file
     * @param key key of the property
     * @return value of the property
     * @throws FileNotFoundException if the property is not found
     * @author The BiquesDAM Team
     */
    fun getProperty(key: String): String {
        val value = properties.getProperty(key)
        if (value != null) {
            return value
        } else {
            throw FileNotFoundException("Property: $key not found in file: $fileName")
        }
    }
}
