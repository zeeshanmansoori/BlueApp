package com.zee.blueapp.domain.useCase

class BaseUseCase(
    val fetchTopicPhotosUseCase: FetchTopicsPhotosUseCase,
    val fetchTopicsUseCase: FetchTopicsUseCase,
    val generateStatsUseCase: GenerateStatsUseCase,
)