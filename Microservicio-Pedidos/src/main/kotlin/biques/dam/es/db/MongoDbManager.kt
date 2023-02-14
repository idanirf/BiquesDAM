package biques.dam.es.db

import biques.dam.es.utils.PropertiesReader
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object MongoDbManager {
    val properties = PropertiesReader("application.properties")
    private lateinit var mongoDbClient: CoroutineClient
    lateinit var database: CoroutineDatabase

    private val STRING_CONNECTION = properties.getProperty("string_connection")

    init {
        mongoDbClient = KMongo.createClient(STRING_CONNECTION).coroutine
        database = mongoDbClient.getDatabase("orders")
    }

}