package com.zee.blueapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zee.blueapp.domain.models.Photo
import com.zee.blueapp.domain.models.Topic
import com.zee.blueapp.domain.useCase.BaseUseCase
import com.zee.blueapp.domain.utils.ApiResult
import com.zee.blueapp.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val baseUseCase: BaseUseCase,
) : ViewModel() {

    private val _carousels = MutableStateFlow<List<Topic>>(emptyList())
    val carousels = _carousels.asStateFlow()

    private val _selectedCarousel = MutableStateFlow<Topic?>(null)
    val selectedCarousel = _selectedCarousel.asStateFlow()

    private val _searchKey = MutableStateFlow<String>("")
    val searchKey = _searchKey.asStateFlow()

    private val _isCarouselLoading = MutableStateFlow<Boolean>(false)
    val isCarouselLoading = _isCarouselLoading.asStateFlow()


    private val _isBottomSheetVisible = MutableStateFlow<Boolean>(false)
    val isBottomSheetVisible = _isBottomSheetVisible.asStateFlow()

    private val _stats = MutableStateFlow<String>("")
    val stats = _stats.asStateFlow()


    val photos = combine(_selectedCarousel, _searchKey) { topic, searchkey ->

        var photos = topic?.photos

        if (topic != null && photos == null) callTopicPhotosApi(topic)

        if (searchkey.isNotBlank()) photos = photos?.filter {
            it.userName.contains(searchkey, true)
        }
        photos
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)


    private val _homeEvents = MutableSharedFlow<HomeEvent>()
    val homeEvents = _homeEvents.asSharedFlow()


    init {
        callCarouselApi()
    }

    private fun callCarouselApi() = viewModelScope.launch {

        baseUseCase.fetchTopicsUseCase().catch {
            _homeEvents.emitNow(
                HomeEvent.ApiFailure(
                    failureReason = it.localizedMessage ?: "",
                )
            )
        }.collect {

            _isCarouselLoading.value = it.isLoading

            if (it is ApiResult.Success) {
                _carousels.value = it.data
            }
            if (it is ApiResult.Failure) {
                _homeEvents.emitNow(
                    HomeEvent.ApiFailure(
                        failureReason = it.error.localizedMessage ?: "",
                    )
                )
            }

        }
    }

    private fun callTopicPhotosApi(topic: Topic) = viewModelScope.launch {

        val perPage = Random.nextInt(AppConstants.PER_PAGE_LOWER_BOUND,AppConstants.PER_PAGE_UPPER_BOUND)

        baseUseCase.fetchTopicPhotosUseCase(topicId = topic.id,perPage).catch {
            _homeEvents.emitNow(HomeEvent.ApiFailure(failureReason = it.localizedMessage ?: ""))
        }.collect {

            if (it is ApiResult.Success) {
                updateTopicsWithPhotos(topic, it.data)
            }
            if (it is ApiResult.Failure) {
                _homeEvents.emitNow(HomeEvent.ApiFailure(failureReason = it.error.localizedMessage ?: "",))
            }

        }
    }

    private fun updateTopicsWithPhotos(topic: Topic, data: List<Photo>) {
        val oldList = _carousels.value.toMutableList()
        val index = oldList.indexOf(topic)

        oldList[index] = topic.copy(photos = data)

        _carousels.value = oldList
        _selectedCarousel.value = topic.copy(photos = data)
    }

    fun onHorizontalPageChanged(pageIndex: Int) {
        val items = _carousels.value
        if (items.isEmpty()) return

        _selectedCarousel.value = items[pageIndex]

    }

    fun onSearchKeyChanged(searchKey: String) {
        _searchKey.value = searchKey
    }

    fun showBottomSheet() {
        _isBottomSheetVisible.value = true
        updateStats()
    }

    fun hideBottomSheet() {
        _isBottomSheetVisible.value = false
    }

    private fun updateStats() = viewModelScope.launch {
        _selectedCarousel.value?.let { topic ->
            val stats = baseUseCase.generateStatsUseCase(topic)
            _stats.value = stats
        }
    }

    sealed class HomeEvent {
        data class ApiFailure(
            val failureReason: String = "",
            val res: Int? = null,
        ) : HomeEvent()

    }


    private fun <T> MutableSharedFlow<T>.emitNow(value: T) {
        viewModelScope.launch {
            emit(value)
        }
    }

}