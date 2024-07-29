package com.zee.blueapp.ui.home.stats

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zee.blueapp.databinding.StatsDialogBinding
import com.zee.blueapp.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zee.blueapp.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StatsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: StatsDialogBinding? = null
    private val binding: StatsDialogBinding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StatsDialogBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.selectedCarousel.collectLatest { topic ->
                        topic ?: return@collectLatest
                        val photoCount = topic.photos?.size ?: 0
                        binding.tvTitle.text =
                            getString(R.string.category_tile_text, topic.title, photoCount)
                    }

                }

                launch {
                    homeViewModel.stats.collectLatest {
                        binding.tvContent.text =
                            getString(R.string.top_3_character_occurrences_text, it)
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        homeViewModel.hideBottomSheet()
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}