package com.zee.blueapp.data.dto

import com.zee.blueapp.domain.models.Topic
import com.zee.blueapp.domain.utils.DTOMapper
import com.google.gson.annotations.SerializedName

data class TopicResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("cover_photo")
    val coverPhoto: PhotoResponse,
) : DTOMapper<Topic> {
    override fun map(): Topic {
        return Topic(
            id = id,
            title = title,
            description = description,
            coverPhoto = coverPhoto.map()
        )
    }
}