package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTitle(categoryName: String) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = categoryName, style = TextStyle()) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundMainWhite)
    )
}