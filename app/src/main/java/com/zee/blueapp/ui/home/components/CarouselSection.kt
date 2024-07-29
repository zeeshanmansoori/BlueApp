package com.zee.blueapp.ui.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.zee.blueapp.R
import com.zee.blueapp.domain.models.Topic

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    pager: List<Topic> = emptyList(),
    onPageChange: (Int) -> Unit = {}
) {

    val height = dimensionResource(id = R.dimen.carousel_section_size)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            val state = rememberPagerState {
                pager.size
            }

            LaunchedEffect(key1 = state) {
                snapshotFlow {
                    state.currentPage
                }.collect {
                    onPageChange(it)
                }
            }

            Column {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = state,
                ) { pageIndex ->
                    Image(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_20dp)),
                        photo = pager[pageIndex].coverPhoto
                    )

                }
                PagerIndicator(pagerState = state)
            }
        }
    }


}

@Preview
@Composable
fun CarouselSectionPreview() {
    CarouselSection()
}