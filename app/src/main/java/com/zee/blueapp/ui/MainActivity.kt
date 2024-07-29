package com.zee.blueapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.zee.blueapp.databinding.ActivityMainBinding
import com.zee.blueapp.ui.home.adapter.CarouselAdapter
import com.zee.blueapp.ui.home.adapter.PhotosAdapter
import com.zee.blueapp.ui.home.stats.StatsBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zee.blueapp.ui.home.HomeViewModel
import com.zee.blueapp.utils.getErrorMsg
import com.zee.blueapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val carouselAdapter = CarouselAdapter()
    private val photosAdapter = PhotosAdapter()
    private val homeViewModel: HomeViewModel by viewModels()
    private var btmSheet: BottomSheetDialogFragment? = null

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            homeViewModel.onHorizontalPageChanged(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.run {
            setContentView(root)

            carouselPager.adapter = carouselAdapter
            rcvPhotos.adapter = photosAdapter

            TabLayoutMediator(pagerIndicator, carouselPager) { _, _ -> }.attach()

            carouselPager.registerOnPageChangeCallback(pageChangeCallback)
            etSearch.doOnTextChanged { text, _, _, _ ->
                homeViewModel.onSearchKeyChanged(text?.toString() ?: "")
            }

        }

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.fab.setOnClickListener {
            homeViewModel.showBottomSheet()
        }
    }

    private fun setObservers() = lifecycleScope.launch {

        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                homeViewModel.isCarouselLoading.collectLatest {
                    binding.loader.isVisible = it
                }
            }

            launch {
                homeViewModel.carousels.collectLatest {
                    carouselAdapter.submitList(it)
                }
            }
            launch {
                homeViewModel.photos.collectLatest {
                    val isLoaderVisible = it == null
                    val isEmptyListMsgVisible = it != null && it.isEmpty()
                    binding.photosLoader.isVisible = isLoaderVisible
                    binding.tvEmptyListMsg.isVisible = isEmptyListMsgVisible
                    photosAdapter.submitList(it)
                }
            }

            launch {
                homeViewModel.isBottomSheetVisible.collectLatest { show ->

                    if (!show) {
                        btmSheet?.dismiss()
                        btmSheet = null
                        return@collectLatest
                    }
                    if (btmSheet != null && btmSheet!!.isVisible) return@collectLatest
                    showBottomSheet()
                }
            }

            launch {
                homeViewModel.homeEvents.collect { event ->
                    if (event is HomeViewModel.HomeEvent.ApiFailure){
                        showToast(event.getErrorMsg(this@MainActivity))
                    }
                }
            }
        }
    }

    private fun showBottomSheet() {
        btmSheet?.dismiss()
        val btmS = StatsBottomSheetFragment()
        btmSheet = btmS
        btmS.show(supportFragmentManager, null)
    }

    override fun onDestroy() {
        binding.carouselPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroy()
    }
}