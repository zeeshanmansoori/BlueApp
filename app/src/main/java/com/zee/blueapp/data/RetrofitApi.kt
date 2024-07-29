package com.zee.blueapp.data

import com.zee.blueapp.data.dto.PhotoResponse
import com.zee.blueapp.data.dto.TopicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com"
        const val API_ACCESS_KEY = "Client-ID y12WmxEp2c48TuBfmJ24_MF3jET09ywXGpOmRN5DRcU"
    }

    @GET("/photos/random")
    suspend fun getRandomPhotos(@Query("count") count: Int = 10): Response<List<PhotoResponse>>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
    ): Response<List<PhotoResponse>>

    @GET("/topics")
    suspend fun getTopics(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
    ): Response<List<TopicResponse>>

    @GET("/topics/{topic_id}/photos")
    suspend fun getTopicsPhotos(
        @Path("topic_id") topicId: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
    ): Response<List<PhotoResponse>>

}
