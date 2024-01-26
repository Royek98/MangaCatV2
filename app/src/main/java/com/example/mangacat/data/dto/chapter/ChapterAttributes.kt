package com.example.mangacat.data.dto.chapter

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
@SerialName("attributes")
data class ChapterAttributes(
    val volume: String?,
    val chapter: String?,
    val title: String?,
    val translatedLanguage: String,
    val pages: Int,
    @Serializable(with = DateAsLongSerializer::class)
    val updatedAt: String
)

object DateAsLongSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: String) = encoder.encodeString(value)
    override fun deserialize(decoder: Decoder): String {
        var decoded = decoder.decodeString()

        // returns date as yyyy-mm-dd
        return decoded.split("T")[0]
    }
}