package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToolBar() {
    TopAppBar(title = { Text(text = "Digi Bazaar")}, colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundMainWhite),
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "ShoppingCart")
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
            }
        }
    )

}