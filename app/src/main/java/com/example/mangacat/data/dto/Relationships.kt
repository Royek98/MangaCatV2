package com.example.mangacat.data.dto

import android.util.Log
import com.example.mangacat.data.dto.manga.enums.ContentRating
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
abstract class Includes {
    abstract val id: String
    abstract val type: Type
}

@Serializable
data class DefaultRelationships(
    override val id: String,
    override val type: Type
) : Includes()

@Serializable
data class AuthorIncludes(
    val name: String,
    override val id: String,
    override val type: Type
) : Includes()

@Serializable
data class CoverArtIncludes(
    val fileName: String,
    override val id: String,
    override val type: Type
) : Includes()

@Serializable
data class MangaIncludes(
    val related: String? = null,
    val title: English,
    val contentRating: ContentRating? = null,
    override val id: String,
    override val type: Type
) : Includes()

@Serializable
data class ScanlationGroupIncludes(
    override val id: String,
    override val type: Type,
    val name: String,
    val website: String?,
    val description: String?,
    val official: Boolean
): Includes()

@Serializable
data class UserIncludes(
    override val id: String,
    override val type: Type,
    val username: String
): Includes()


object IncludesResponseSerializer :
    JsonTransformingSerializer<List<Includes>>(ListSerializer(Includes.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val attributes: MutableList<JsonElement> = element.jsonArray.map {
            it.jsonObject["attributes"] ?: buildJsonObject { put("attributes", false) }
        }.toMutableList()

        val ids = element.jsonArray.map { it.jsonObject["id"]!! }
        val types = element.jsonArray.map { it.jsonObject["type"]!! }

        for (i in attributes.indices) {
            attributes[i] = buildJsonObject {
                put("id", ids[i])
                put("type", types[i])
                if (!attributes[i].jsonObject.containsKey("attributes")) {
                    put("attributes", true)
                    attributes[i].jsonObject.forEach {
                        put(it.key, it.value)
                    }
                } else {
                    put("attributes", false)
                }
            }

        }

        return JsonArray(attributes)
    }
}


object IncludesPolymorphicSerializer :
    JsonContentPolymorphicSerializer<Includes>(Includes::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Includes> {
        if (element.jsonObject.getValue("attributes").toString().toBoolean()) {
            return when (val type =
                element.jsonObject.getValue("type").toString().filterNot { it == '"' }) {
                Type.AUTHOR.name.lowercase(), Type.ARTIST.name.lowercase() -> AuthorIncludes.serializer()
                Type.COVER_ART.name.lowercase() -> CoverArtIncludes.serializer()
                Type.MANGA.name.lowercase() -> MangaIncludes.serializer()
                Type.USER.name.lowercase() -> UserIncludes.serializer()
                Type.SCANLATION_GROUP.name.lowercase() -> ScanlationGroupIncludes.serializer()
                else -> throw Exception(
                    "Includes type error. Type: $type not found for id ${
                        element.jsonObject.getValue(
                            "id"
                        )
                    }"
                )
            }
        }
        return DefaultRelationships.serializer()
    }
    // element.jsonObject.getValue("attributes").toString().toBoolean()

//    when (element.jsonObject.getValue("type").toString().filterNot { it == '"' }) {
//        Type.AUTHOR.name.lowercase(), Type.ARTIST.name.lowercase() -> AuthorIncludes.serializer()
//        Type.COVER_ART.name.lowercase() -> CoverArtIncludes.serializer()
//        Type.MANGA.name.lowercase() -> MangaIncludes.serializer()
//        else -> DefaultIncludes.serializer()
//    }

//        when (val aa = element.jsonObject) {
//            when(aa.jsonObject.getValue("attributes").toString().toBoolean()) {
//                true -> Log.d("TAG", "selectDeserializer: ")
//                else -> Log.d("TAG", "selectDeserializer: ")
//            }
//        }

}
