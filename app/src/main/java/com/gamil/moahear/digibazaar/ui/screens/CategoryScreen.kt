package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.components.CategoryTitle
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlue
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.viewmodel.CategoryViewModel
import org.koin.compose.koinInject

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = koinInject(),
    categoryName: String,
    onNavigate: (String) -> Unit
) {
    categoryViewModel.getProductsByCategory(categoryName)
    Column(modifier = Modifier.fillMaxSize()) {
        val productsByCategoryName by categoryViewModel.productsByCategoryName.collectAsStateWithLifecycle()
        CategoryTitle(categoryName)
        if (productsByCategoryName.isNotEmpty()) {
            CategoryList(productsByCategoryName, onNavigate)
        }

    }
}

@Composable
fun CategoryList(products: List<ProductsResponse.Product>, onNavigate: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(products.size, key = {
            products[it].productId
        }) {
            ProductOfCategory(products[it], onNavigate)
        }
    }
}

@Composable
fun ProductOfCategory(product: ProductsResponse.Product, onProductClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable {
                onProductClicked(Screen.ProductScreen.withArgs(product.productId))
            }, elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), shape = Shapes.large
    ) {
        Column() {
            AsyncImage(
                modifier = Modifier
                    .height(260.dp)
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Crop,
                model = product.imgUrl,
                contentDescription = null
            )
            Text(
                text = product.name,
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium)
            )
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${product.price} Tomans", style = TextStyle(fontSize = 14.sp))
                Surface(
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                        .clip(
                            Shapes.large
                        ), color = BackgroundBlue
                ) {
                    Text(
                        text = "${product.soldItem} Sold",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

