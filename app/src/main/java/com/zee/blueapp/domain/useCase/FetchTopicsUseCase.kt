package com.zee.blueapp.domain.useCase

import com.zee.blueapp.data.RetrofitApi
import com.zee.blueapp.domain.models.Topic
import com.zee.blueapp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchTopicsUseCase(private val api: RetrofitApi) {

    suspend operator fun invoke(): Flow<ApiResult<List<Topic>>> = flow {
        emit(ApiResult.Loading())
        val result = api.getTopics()
        if (result.isSuccessful) {
            val data = result.body()?.map { it.map() } ?: emptyList()
            emit(ApiResult.Success(data = data))
        } else throw Exception(result.message())
    }

}