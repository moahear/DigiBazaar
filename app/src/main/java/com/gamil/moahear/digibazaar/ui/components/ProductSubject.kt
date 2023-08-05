package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.ui.theme.Shapes

@Composable
fun ProductSubject() {
    Column(modifier = Modifier.padding(top=32.dp)) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "popular",
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(10) {
                SubjectItem()
            }
        }

    }
}

@Composable
fun SubjectItem() {
    Card(modifier = Modifier
        .padding(start = 16.dp)
        .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = Shapes.medium
    ) {
        Column {
            Image(
                modifier = Modifier.width(200.dp),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.img_bags_intro),
                contentDescription = null
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "SubjectItem first",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "SubjectItem second",
                    style = TextStyle(fontSize = 14.sp)
                )
                Text(text = "SubjectItem three", style = TextStyle(color = Color.Gray, fontSize = 12.sp))
            }
        }
    }
}
