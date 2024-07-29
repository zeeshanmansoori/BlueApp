package com.zee.blueapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zee.blueapp.R
import com.zee.blueapp.domain.models.Topic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(topic: Topic, stats: String, onDismiss: () -> Unit = {}) {

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_20dp),
                    end = dimensionResource(id = R.dimen.padding_20dp),
                    bottom = dimensionResource(id = R.dimen.padding_20dp),
                    top = dimensionResource(id = R.dimen.padding_10dp)
                )
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.stastistics_txt),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.title_text_size).value.sp
                )
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_20dp)))
            Text(text = stringResource(R.string.category_tile_text, topic.title))
            Text(text = stringResource(R.string.total_items_text, topic.photos?.size?:0))
            Text(text = stringResource(R.string.top_3_character_occurrences_text, stats))
        }
    }
}