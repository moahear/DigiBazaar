package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.ui.theme.Shapes

@Composable
fun ImageAdvertising() {
        Image(modifier= Modifier.fillMaxWidth().clip(shape = Shapes.medium).padding(top = 32.dp, start = 16.dp, end = 16.dp).clickable {  },painter = painterResource(id = R.drawable.img_bags_intro), contentDescription = "", contentScale = ContentScale.FillWidth)

}