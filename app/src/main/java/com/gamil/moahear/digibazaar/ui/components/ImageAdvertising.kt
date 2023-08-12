package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.Shapes

@Composable
fun ImageAdvertising(ad: SliderPicsResponse.Ad, onProductClicked: (String) -> Unit) {
    AsyncImage(
        modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp)
                .clip(shape = Shapes.medium)
                .clickable {
                        onProductClicked(
                                Screen.ProductScreen.withArgs(ad.productId)
                        )
                }, model = ad.imageURL, contentDescription = "", contentScale = ContentScale.FillWidth
    )

}