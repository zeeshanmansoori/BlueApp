package com.zee.blueapp.domain.useCase

class BaseUseCase(
    val fetchTopicsPhotosUseCase: FetchTopicsPhotosUseCase,
    val fetchTopicsUseCase: FetchTopicsUseCase,
    val generateStatsUseCase: GenerateStatsUseCase,
)