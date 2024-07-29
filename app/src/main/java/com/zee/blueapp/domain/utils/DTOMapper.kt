package com.zee.blueapp.domain.utils

interface DTOMapper<model : Any> {
    fun map(): model
}