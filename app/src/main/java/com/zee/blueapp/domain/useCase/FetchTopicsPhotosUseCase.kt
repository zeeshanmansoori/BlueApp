package com.zee.blueapp.domain.useCase

import com.zee.blueapp.data.RetrofitApi
import com.zee.blueapp.domain.models.Photo
import com.zee.blueapp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchTopicsPhotosUseCase(private val api: RetrofitApi) {

    suspend operator fun invoke(topicId: String, perPage: Int): Flow<ApiResult<List<Photo>>> = flow {
            emit(ApiResult.Loading())
            val result = api.getTopicsPhotos(topicId = topicId, perPage = perPage)
            if (result.isSuccessful) {
                val data = result.body()?.map { it.map() } ?: emptyList()
                emit(ApiResult.Success(data = data))
            } else throw Exception(result.message())
    }

}