package com.zee.blueapp.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.zee.blueapp.R
import com.zee.blueapp.domain.models.Photo

@Composable
fun ListSingleItem(modifier: Modifier, photo: Photo) {
    val cardShape = RoundedCornerShape(dimensionResource(id = R.dimen.carousel_rounded_size_15dp))
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.list_image_rounded_size))

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = cardShape
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_10dp))
                    .size(dimensionResource(id = R.dimen.list_item_image_size_60dp)),
                shape = shape,
                photo = photo,
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_5dp),
                        end = dimensionResource(id = R.dimen.padding_10dp)
                    )
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = photo.userName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.subtitle_text_size).value.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_5dp)))
                Text(
                    text = photo.description,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = dimensionResource(id = R.dimen.description_text_size).value.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
fun ListSingleItemPreview() {

}