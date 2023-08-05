package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.ui.theme.BackgroundCard
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes

@Composable
fun CategoryTop() {
    LazyRow(modifier = Modifier.padding(16.dp), contentPadding = PaddingValues(end = 16.dp)) {
        items(10) {
            CategoryItem()
        }
    }
}

@Composable
fun CategoryItem() {
    Column(modifier = Modifier
        .padding(start = 16.dp)
        .clickable {

        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(modifier = Modifier.clip(Shapes.medium), color = BackgroundCard) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .background(BackgroundMainWhite), painter = painterResource(
                    id = R.drawable.img_bags_intro
                ), contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(4.dp), text = "salam mostafa",
            style = TextStyle(color = Color.Gray)
        )
    }
}
