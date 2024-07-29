package com.zee.blueapp.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.zee.blueapp.R
import com.zee.blueapp.domain.models.Photo

@Composable
fun Image(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(dimensionResource(id = R.dimen.carousel_rounded_size_15dp)),
    photo: Photo,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape),
            model = photo.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.image_place_holder),
        )
    }
}

@Preview
@Composable
fun CarouselImagePreview() {}