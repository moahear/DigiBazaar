package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.data.model.CartInfoResponse
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.BackgroundAddToCart
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlueLight
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.utils.separateDigit
import com.gamil.moahear.digibazaar.viewmodel.CartViewModel
import org.koin.compose.koinInject

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = koinInject(),
    onBackClicked: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val productsInCart by cartViewModel.productSInCart.collectAsStateWithLifecycle()
    val totalPrice by cartViewModel.totalPrice.collectAsStateWithLifecycle()
    val isChangingNumber by cartViewModel.isChangingNumber.collectAsStateWithLifecycle()
    val cartSize by cartViewModel.cartSize.collectAsStateWithLifecycle()
    var isVisibleDialog by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 74.dp)
        ) {
            CartToolbar(onBackClicked = onBackClicked, onProfileClickListener = onNavigate)
            if (cartSize > 0) {
                CartItemsList(
                    products = productsInCart,
                    isChangingNumber = isChangingNumber,
                    onAddItemClickListener = {
                        cartViewModel.addToCart(it)
                    },
                    onRemoveItemClick = {
                        cartViewModel.removeFromCart(it)
                    },
                    onItemClickListener = {
                        onNavigate(it)
                    }
                )
            } else if (cartSize != -1) {
                ShowEmptyCartAnimation()
            }
        }
    }
}

@Composable
fun ShowEmptyCartAnimation() {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.no_data))
    LottieAnimation(
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartToolbar(
    onBackClicked: () -> Unit,
    onProfileClickListener: (String) -> Unit
) {
    CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth(), title = {
        Text(text = "Cart info")
    }, navigationIcon = {
        IconButton(onClick = onBackClicked) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    }, colors = TopAppBarDefaults.topAppBarColors(BackgroundMainWhite), actions = {
        IconButton(onClick = { onProfileClickListener(Screen.ProfileScreen.route) }) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        }
    }
    )
}

@Composable
fun CartItemsList(
    products: List<CartInfoResponse.Product>,
    isChangingNumber: Pair<String, Boolean>,
    onAddItemClickListener: (String) -> Unit,
    onRemoveItemClick: (String) -> Unit,
    onItemClickListener: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)) {
        items(count = products.size, key = {
            products[it].productId
        }) {
            CartItem(
                product = products[it],
                isChangingNumber = isChangingNumber,
                onAddItemClickListener = onAddItemClickListener,
                onRemoveItemClick = onRemoveItemClick,
                onItemClickListener = onItemClickListener
            )
        }
    }
}

@Composable
fun CartItem(
    product: CartInfoResponse.Product,
    isChangingNumber: Pair<String, Boolean>,
    onAddItemClickListener: (String) -> Unit,
    onRemoveItemClick: (String) -> Unit,
    onItemClickListener: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable {
                onItemClickListener.invoke(Screen.ProductScreen.withArgs(product.productId))
            },
        colors = CardDefaults.cardColors(containerColor = BackgroundMainWhite),
        shape = Shapes.large
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = product.imgUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "From product.name group",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = product.category,
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = "Original product",
                        style = TextStyle(fontSize = 14.sp)
                    )
                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = "Available in stock room",
                        style = TextStyle(fontSize = 14.sp)
                    )

                    Surface(
                        modifier = Modifier
                            .padding(top = 18.dp, bottom = 6.dp)
                            .clip(shape = Shapes.large),
                        color = BackgroundAddToCart
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            text = separateDigit("${product.price.toInt() * product.quantity.toInt()}"),
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        )
                    }
                }
                Surface(
                    modifier = Modifier
                        .padding(bottom = 14.dp, end = 8.dp)
                        .align(alignment = Alignment.Bottom),
                    color = BackgroundMainWhite
                ) {
                    Card(
                        border = BorderStroke(width = 2.dp, color = BackgroundBlueLight),
                        colors = CardDefaults.cardColors(containerColor = BackgroundMainWhite)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            IconButton(onClick = { onRemoveItemClick(product.productId) }) {
                                Icon(
                                    modifier = Modifier.padding(horizontal = 6.dp), imageVector =
                                    if (product.quantity.toInt() == 1) Icons.Default.Delete
                                    else Icons.Default.Remove, contentDescription = null
                                )
                            }

                            if (isChangingNumber.first == product.productId && isChangingNumber.second) {
                                Text(
                                    modifier = Modifier.padding(bottom = 12.dp),
                                    text = "...",
                                    style = TextStyle(fontSize = 18.sp)
                                )
                            } else {
                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text = product.quantity,
                                    style = TextStyle(fontSize = 18.sp)
                                )
                            }

                            IconButton(onClick = { onAddItemClickListener(product.productId) }) {
                                Icon(
                                    modifier = Modifier.padding(horizontal = 6.dp), imageVector =
                                    Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}