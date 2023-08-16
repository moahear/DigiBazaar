package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeNumberCart(badgeNumber: Int, onCartClicked: () -> Unit) {
    IconButton(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(0.21f),
        onClick = onCartClicked
    ) {
        if (badgeNumber == 0) Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null
        )
        else
            BadgedBox(badge = {
                Badge(modifier = Modifier.padding(top = 12.dp)) {
                    Text(text = badgeNumber.toString())
                }
            }) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
            }
    }
}