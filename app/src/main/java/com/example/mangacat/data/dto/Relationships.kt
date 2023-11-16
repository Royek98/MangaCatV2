package com.example.mangacat.data.dto

import com.example.mangacat.data.dto.response.enums.Type
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put


@Serializable
data class DefaultRelationships(
    val id: String,
    val type: Type
)

@Serializable
abstract class Includes {
    abstract val id: String?
    abstract val type: String?
}

@Serializable
data class Author(
    val name: String,
    val imageUrl: String?,
    val biography: English?,
    val twitter: String?,
    val pixiv: String?,
    val melonBook: String?,
    val fanBox: String?,
    val nicoVideo: String?,
    val skeb: String?,
    val fantia: String?,
    val tumblr: String?,
    val youtube: String?,
    val weibo: String?,
    val naver: String?,
    val website: String?,
    val version: Int?,
    val createdAt: String?,
    val updatedAt: String?, override val id: String? = null, override val type: String? = null
) : Includes()

@Serializable
data class CoverArt(
    val description: String? = null, val fileName: String? = null,
    override val id: String? = null,
    override val type: String? = null
) : Includes()

object IncludesResponseSerializer :
    JsonTransformingSerializer<List<Includes>>(ListSerializer(Includes.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val attributes: MutableList<JsonElement> = element.jsonArray.map {
            it.jsonObject["attributes"] ?: buildJsonObject { put("attributes", "") }
        }.toMutableList()

        val ids = element.jsonArray.map { it.jsonObject["id"]!! }
        val types = element.jsonArray.map { it.jsonObject["type"]!! }


        for (i in attributes.indices) {
            attributes[i] = buildJsonObject {
                attributes[i].jsonObject.forEach {
                    put(it.key, it.value)
                }
                put("id", ids[i])
                put("type", types[i])
            }

        }

        return JsonArray(attributes)
    }
}


object IncludesPolymorphicSerializer :
    JsonContentPolymorphicSerializer<Includes>(Includes::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Includes> =
        when (element.jsonObject.getValue("type").toString().filterNot { it == '"' }) {
            Type.AUTHOR.name.lowercase() -> Author.serializer()
            else -> CoverArt.serializer()
        }
}
