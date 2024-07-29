package com.zee.blueapp.di

import com.zee.blueapp.data.RetrofitApi
import com.zee.blueapp.domain.useCase.BaseUseCase
import com.zee.blueapp.domain.useCase.FetchTopicsPhotosUseCase
import com.zee.blueapp.domain.useCase.FetchTopicsUseCase
import com.zee.blueapp.domain.useCase.GenerateStatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUseCase(
        fetchTopicsUseCase: FetchTopicsUseCase,
        fetchPhotosUseCase: FetchTopicsPhotosUseCase,
        generateStatsUseCase: GenerateStatsUseCase
    ): BaseUseCase {
        return BaseUseCase(
            fetchTopicsUseCase = fetchTopicsUseCase,
            fetchTopicsPhotosUseCase = fetchPhotosUseCase,
            generateStatsUseCase = generateStatsUseCase,
        )
    }

    @Provides
    fun providesFetchTopicsUseCase(api: RetrofitApi): FetchTopicsUseCase {
        return FetchTopicsUseCase(api)
    }

    @Provides
    fun providesFetchTopicPhotosUseCase(api: RetrofitApi): FetchTopicsPhotosUseCase {
        return FetchTopicsPhotosUseCase(api)
    }

    @Provides
    fun providesGenerateStatsUseCase(): GenerateStatsUseCase {
        return GenerateStatsUseCase()
    }


}