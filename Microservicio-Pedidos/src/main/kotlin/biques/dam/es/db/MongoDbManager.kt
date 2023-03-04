package biques.dam.es.db

import biques.dam.es.utils.PropertiesReader
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.bson.UuidRepresentation
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


object MongoDbManager {
    val properties = PropertiesReader("application.properties")
    private lateinit var mongoDbClient: CoroutineClient
    lateinit var database: CoroutineDatabase

    private val STRING_CONNECTION = properties.getProperty("string_connection")
    private val STRING_CONNECTION_TEST = properties.getProperty("string_connection_test")

    init {
        val clientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(STRING_CONNECTION))
            .uuidRepresentation(UuidRepresentation.JAVA_LEGACY).build()
        mongoDbClient = KMongo.createClient(clientSettings).coroutine
        database = mongoDbClient.getDatabase("order")
    }

}