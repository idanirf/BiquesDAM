package es.dam.bique.microservicioproductoservicios.db

/*import es.dam.bique.microservicioproductoservicios.utils.PropertiesReader
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object MongoDbManager {

    private val properties = PropertiesReader("application.properties")
    private var mongoDbClient: CoroutineClient
    var database: CoroutineDatabase

    private val STRING_CONNECTION = properties.getProperty("database_connection")

    init {
        mongoDbClient = KMongo.createClient(STRING_CONNECTION).coroutine
        database = mongoDbClient.getDatabase("products&services")
    }

}

 */