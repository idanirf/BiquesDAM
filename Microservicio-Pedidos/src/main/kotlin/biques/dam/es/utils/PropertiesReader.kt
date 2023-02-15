package biques.dam.es.utils

import java.io.FileNotFoundException
import java.util.*


class PropertiesReader(private val fileName: String) {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader.getResourceAsStream(fileName)
        if (file != null) {
            properties.load(file)
        } else {
            throw FileNotFoundException("File not found: $fileName")
        }
    }

    fun getProperty(key: String): String {
        val value = properties.getProperty(key)
        if (value != null) {
            return value
        } else {
            throw FileNotFoundException("Property $key not found in file: $fileName")
        }
    }
}
