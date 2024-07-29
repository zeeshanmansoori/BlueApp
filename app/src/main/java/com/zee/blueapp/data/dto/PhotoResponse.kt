package com.zee.blueapp.data.dto

import com.zee.blueapp.domain.models.Photo
import com.zee.blueapp.domain.utils.DTOMapper
import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("user")
    val user: UserResponse,
    @SerializedName("urls")
    val urls: PhotoUrlsResponse,
) : DTOMapper<Photo> {

    override fun map(): Photo {
        return Photo(
            id = id,
            color = color,
            description = description ?: "",
            url = urls.regular,
            userName = user.name,
        )
    }
}
