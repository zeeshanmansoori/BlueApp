package com.zee.blueapp.data.dto

import com.google.gson.annotations.SerializedName

data class SearchPhotosResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("results")
    val results: List<PhotoResponse>,
)