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
import com.gamil.moahear.digibazaar.ui.theme.BackgroundCard
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes

@Composable
fun CategoryTop(categories:List<Pair<String,Int>>) {
    LazyRow(modifier = Modifier.padding(top=16.dp), contentPadding = PaddingValues(end = 16.dp)) {
        items(categories.size,key = {
            categories[it].first
        }) {
            CategoryItem(categories[it])
        }
    }
}

@Composable
fun CategoryItem(categoryItem:Pair<String,Int>) {
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
                    id = categoryItem.second
                ), contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(4.dp), text = categoryItem.first,
            style = TextStyle(color = Color.Gray)
        )
    }
}
