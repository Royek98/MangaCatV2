package com.example.mangacat.data.dto.libraryStatus

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put

@Serializable
data class LibraryResponse(
    val result: String,
    @Serializable(with = LibraryResponseSerializer::class)
    val statuses: List<Statuses>
)


object LibraryResponseSerializer : JsonTransformingSerializer<List<Statuses>>(ListSerializer(Statuses.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        val result = mutableListOf<JsonElement>()
        element.jsonObject.forEach {element ->
            result.add(
                buildJsonObject {
                    put("mangaId", element.key)
                    put("statusType", element.value)
                }
            )
        }
        return JsonArray(result)
    }
}