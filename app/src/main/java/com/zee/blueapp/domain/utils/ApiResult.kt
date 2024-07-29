package com.zee.blueapp.domain.utils

sealed class ApiResult<T> {

    open val isLoading: Boolean = false
    open val data: T? = null
    open val error: Exception? = null

    class Loading<T>(override val isLoading: Boolean = true) : ApiResult<T>()
    class Success<T>(override val data: T) : ApiResult<T>()
    class Failure<T>(override val error: Exception) : ApiResult<T>()

    override fun toString(): String {
        return "isLoading $isLoading, data $data,error $error"
    }
}