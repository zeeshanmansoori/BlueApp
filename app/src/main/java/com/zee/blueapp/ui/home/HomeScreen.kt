package com.zee.blueapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zee.blueapp.R
import com.zee.blueapp.ui.home.components.CarouselSection
import com.zee.blueapp.ui.home.components.HomeBottomSheet
import com.zee.blueapp.ui.home.components.ListSingleItem
import com.zee.blueapp.ui.home.components.ProgressLoader
import com.zee.blueapp.ui.home.components.SearchBar
import com.zee.blueapp.utils.AppConstants
import com.zee.blueapp.utils.collectAsEvent
import com.zee.blueapp.utils.getErrorMsg
import com.zee.blueapp.utils.showToast

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val loading by homeViewModel.isCarouselLoading.collectAsState()
    val carousels by homeViewModel.carousels.collectAsState()
    val selectedTopic by homeViewModel.selectedCarousel.collectAsState()
    val photos by homeViewModel.photos.collectAsState()
    val searchKey by homeViewModel.searchKey.collectAsState()
    val isBottomSheetVisible by homeViewModel.isBottomSheetVisible.collectAsState()
    val stats by homeViewModel.stats.collectAsState()
    val context = LocalContext.current

    homeViewModel.homeEvents.collectAsEvent { event ->
        if (event is HomeViewModel.HomeEvent.ApiFailure){
            context.showToast(event.getErrorMsg(context))
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = homeViewModel::showBottomSheet) {
            Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
        }
    }) {

        LazyColumn(
            modifier = Modifier
                .padding(it)

        ) {
            item {
                CarouselSection(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.padding_10dp)),
                    pager = carousels,
                    isLoading = loading,
                    onPageChange = homeViewModel::onHorizontalPageChanged
                )
            }
            stickyHeader(true) {
                SearchBar(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(vertical = dimensionResource(id = R.dimen.padding_10dp))
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_20dp)),
                    value = searchKey,
                    onValueChange = homeViewModel::onSearchKeyChanged
                )
            }
            if (selectedTopic == null || photos == null) {
                item {
                    ProgressLoader()
                }
            } else photos?.let { list ->

                if (list.isEmpty()) item {
                    Box(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.padding_50dp))
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.empty_list_txt))

                    }
                }
                else itemsIndexed(list) { index, item ->
                    val lastIndex = list.size - 1
                    var bottom = dimensionResource(id = R.dimen.padding_2dp)
                    if (index == lastIndex) bottom =
                        dimensionResource(id = R.dimen.padding_20dp)

                    val paddingValues = PaddingValues(
                        top = dimensionResource(id = R.dimen.padding_2dp),
                        start = dimensionResource(id = R.dimen.padding_20dp),
                        end = dimensionResource(id = R.dimen.padding_20dp),
                        bottom = bottom,
                    )
                    ListSingleItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
                        photo = item,
                    )
                }
            }

        }

        if (isBottomSheetVisible) selectedTopic?.let { topic ->
            HomeBottomSheet(
                topic,
                stats,
                homeViewModel::hideBottomSheet
            )
        }

    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(homeViewModel = hiltViewModel())
}
