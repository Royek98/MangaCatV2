package com.example.mangacat.data.dto.manga.enums

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

//@Serializable(with = ContentRating.ContentRatingSerializer::class)
@Serializable
enum class ContentRating {
    @SerialName("safe")
    SAFE,

    @SerialName("suggestive")
    SUGGESTIVE,

    @SerialName("erotica")
    EROTICA,

//    @SerialName("pornographic")
    PORNOGRAPHIC;

//    @Serializer(forClass = ContentRating::class)
//    object ContentRatingSerializer : KSerializer<ContentRating> {
//        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("contentRating", PrimitiveKind.STRING)
//        override fun serialize(encoder: Encoder, value: ContentRating) {
//            encoder.encodeString(value.toString().lowercase())
//        }
//        override fun deserialize(decoder: Decoder): ContentRating {
//            // Admittedly, this would accept "Error" in addition to "error".
//            return ContentRating.valueOf(decoder.decodeString().uppercase())
//        }
//    }
}