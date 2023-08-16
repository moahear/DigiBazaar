package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.utils.separateDigit

@Composable
fun ProductSubject(
    subject: String,
    products: List<ProductsResponse.Product>,
    onProductClicked: (String) -> Unit
) {
    Column(modifier = Modifier.padding(top = 32.dp)) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = subject,
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(products.size) {
                SubjectItem(products[it], onProductClicked)
            }
        }

    }
}

@Composable
fun SubjectItem(product: ProductsResponse.Product, onProductClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onProductClicked(Screen.ProductScreen.withArgs(product.productId)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = Shapes.medium
    ) {
        Column {
            /* val matrix = ColorMatrix()
             matrix.setToSaturation(25F)*/
            /* val colorMatrix = floatArrayOf(
                 0f, 0f, 0f, 0f, 0f,
                 0f, 1f, 0f, 0f, 0f,
                 0f, 0f, 1f, 0f, 0f,
                 0f, 0f, 0f, 1f, 0f
             )*/
            val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
            AsyncImage(
                modifier = Modifier
                    .size(260.dp)
                    .background(surfaceVariantColor),
                colorFilter = ColorFilter.tint(
                    color = surfaceVariantColor,
                    blendMode = BlendMode.Darken
                )
                /*.drawBehind {
                    drawRect(surfaceVariantColor)
                }, ColorFilter.colorMatrix(ColorMatrix(colorMatrix))*/,
                contentScale = ContentScale.Fit,
                model = product.imgUrl,
                contentDescription = null
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.name,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = separateDigit(product.price),
                    style = TextStyle(fontSize = 14.sp)
                )
                Text(
                    text = "${product.soldItem} Sold",
                    style = TextStyle(color = Color.Gray, fontSize = 12.sp)
                )
            }
        }
    }
}
