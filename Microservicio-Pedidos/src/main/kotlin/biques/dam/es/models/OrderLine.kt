package biques.dam.es.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import serializers.UUIDSerializer
import java.util.UUID

@Serializable
data class OrderLine(
    @BsonId @Contextual
    val id: Id<OrderLine> = newId(),
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),
    //@Contextual
    //val product: Id<OnSale>,
    val amount: Int,
    val price: Float,
    val total: Int,
    //@Contextual
    //val employee: Id<User>
    )