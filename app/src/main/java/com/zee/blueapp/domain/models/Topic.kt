package com.zee.blueapp.domain.models

data class Topic(
    val id: String,
    val title: String,
    val description: String,
    val coverPhoto: Photo,
    val photos: List<Photo>? = null,
)