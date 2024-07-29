package com.zee.blueapp.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zee.blueapp.R

@Composable
fun ProgressLoader() {
    Box(
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_50dp))
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.padding_50dp)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun ProgressLoaderPreview() {
    ProgressLoader()
}