package com.gamil.moahear.digibazaar.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.viewmodel.ProductViewModel
import org.koin.compose.koinInject

@SuppressLint("SuspiciousIndentation")
@Composable
fun ProductScreen(
    productId: String,
    productViewModel: ProductViewModel = koinInject(),
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit, onNavigate: (String) -> Unit
) {
    productViewModel.getProduct(productId)
    val product by productViewModel.product.collectAsStateWithLifecycle()
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(bottom = 58.dp)
            ) {
                ProductToolbar(
                    productName = "ssss",
                    badgeNumber = 20,
                    onCartClicked = onCartClicked,
                    onBackClicked = onBackClicked
                )
                ProductItem(
                   product,
                    onCategoryClicked = {
                        onNavigate(Screen.CategoryScreen.withArgs(it))
                    })

            }
            AddToCart()
        }
}


@Composable
fun ProductItem(product: ProductsResponse.Product, onCategoryClicked: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        ProductDetails(product = product, onCategoryClicked = onCategoryClicked)
    }
}

@Composable
fun ProductDetails(product: ProductsResponse.Product, onCategoryClicked: (String) -> Unit) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(Shapes.medium),
        model = product.imgUrl,
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
    Text(
        modifier = Modifier.padding(top = 14.dp),
        text = product.name,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
    )
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = product.detailText,
        style = TextStyle(fontSize = 14.sp, textAlign = TextAlign.Justify)
    )
    TextButton(onClick = { onCategoryClicked(product.category) }) {
        Text(text = "#${product.category}", style = TextStyle(fontSize = 12.sp))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductToolbar(
    productName: String,
    badgeNumber: Int,
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit
) {
    CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth(), title = {
        Text(text = productName)
    }, navigationIcon = {
        IconButton(onClick = onBackClicked) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    }, colors = TopAppBarDefaults.topAppBarColors(BackgroundMainWhite), actions = {

        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(0.2f),
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
    )
}

@Composable
fun AddToCart() {

}
