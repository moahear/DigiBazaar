package com.gamil.moahear.digibazaar.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.data.model.Comment
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.components.BadgeNumberCart
import com.gamil.moahear.digibazaar.ui.theme.BackgroundAddToCart
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlueLight
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainBlack
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.utils.separateDigit
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
    productViewModel.getProduct(productId, true)
    val context = LocalContext.current
    val product by productViewModel.product.collectAsStateWithLifecycle()
    val comments by productViewModel.comments.collectAsStateWithLifecycle()
    val badgeNumber by productViewModel.badgeNumber.collectAsStateWithLifecycle()
    val isShowAddingToCartAnimation by productViewModel.isShowAddingToCartAnimation.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(bottom = 58.dp)
        ) {
            ProductToolbar(
                productName = product.name,
                badgeNumber = badgeNumber,
                onCartClicked = onCartClicked,
                onBackClicked = onBackClicked
            )
            ProductItem(
                product,
                comments = comments.sortedByDescending {
                    it.commentId.toInt()
                },
                onAddComment = {
                    productViewModel.addComment(productId, it) { result ->
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                    }
                },
                onCategoryClicked = {
                    onNavigate(Screen.CategoryScreen.withArgs(it))
                })
        }
        AddToCart(product.price, isShowAddingToCartAnimation) {
            //check internet later
            productViewModel.addToCart(productId) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun ProductItem(
    product: ProductsResponse.Product,
    comments: List<Comment>,
    onAddComment: (String) -> Unit,
    onCategoryClicked: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        ProductDetails(product = product, onCategoryClicked = onCategoryClicked)
        Divider(
            modifier = Modifier
                .padding(top = 14.dp, bottom = 14.dp)
                .fillMaxWidth(), color = Color.LightGray, thickness = 1.dp
        )
        ProductExtraInfo(product, comments.size.toString())
        Divider(
            modifier = Modifier
                .padding(top = 14.dp, bottom = 4.dp)
                .fillMaxWidth(), color = Color.LightGray, thickness = 1.dp
        )
        ProductComments(comments, onAddComment)
    }
}

@Composable
fun ProductComments(comments: List<Comment>, onAddComment: (String) -> Unit) {
    var isShowingDialog by remember {
        mutableStateOf(false)
    }
    if (comments.isNotEmpty()) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Comments",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                TextButton(onClick = {
                    //check internet later
                    isShowingDialog = true
                    //if not internet show message

                }) {
                    Text(
                        text = "Add new comment",
                        style = TextStyle(fontSize = 14.sp, color = BackgroundBlueLight)
                    )
                }
            }
        }
        comments.forEach {
            ProductComment(comment = it)
        }
        /*LazyColumn() {
            items(comments.size, key = {
                comments[it].commentId
            }) {
                ProductComment(comment = comments[it])
            }
        }*/
    } else {
        TextButton(onClick = {
            //check internet later
            isShowingDialog = true
            //if not internet show message

        }) {
            Text(
                text = "Add new comment",
                style = TextStyle(fontSize = 14.sp, color = BackgroundBlueLight)
            )
        }
    }
    if (isShowingDialog) {
        AddCommentDialog(onDismiss = {
            isShowingDialog = false
        }, onOK = {
            onAddComment(it)
        })
    }
}

@Composable
fun AddCommentDialog(onDismiss: () -> Unit, onOK: (String) -> Unit) {
    var comment by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = Shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Write comments",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(value = comment, placeholder = {
                    Text(text = "Write your comment")
                }, onValueChange = {
                    comment = it
                }, label = {
                    Text(text = "comment")
                }, shape = Shapes.medium)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(modifier = Modifier.padding(end = 16.dp), onClick = onDismiss) {
                        Text(
                            text = "Cancel",
                            style = TextStyle(
                                color = BackgroundBlueLight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    TextButton(onClick = {
                        if (comment.isNotEmpty() && comment.isNotBlank()) {
                            //check internet
                            onOK(comment)
                            onDismiss.invoke()
                        } else {
                            //show message
                        }
                    }) {
                        Text(
                            text = "Ok",
                            style = TextStyle(
                                color = BackgroundBlueLight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun ProductComment(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundMainWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = Shapes.large
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = comment.userEmail,
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
            )
            Text(text = comment.text, style = TextStyle(fontSize = 14.sp))
        }
    }
}

@Composable
fun ProductExtraInfo(product: ProductsResponse.Product, commentNumber: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.background(BackgroundBlueLight),
                imageVector = Icons.Default.Message,
                contentDescription = null,
                colorFilter = ColorFilter.tint(BackgroundMainWhite)
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = "$commentNumber Comments",
                fontSize = 13.sp
            )
        }


        Row(
            modifier = Modifier.padding(top = 6.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.background(BackgroundBlueLight),
                imageVector = Icons.Default.ShoppingBasket,
                contentDescription = null,
                colorFilter = ColorFilter.tint(BackgroundMainWhite)
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = product.material,
                fontSize = 13.sp
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.background(BackgroundBlueLight),
                    imageVector = Icons.Default.ShoppingCartCheckout,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(BackgroundMainWhite)
                )
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "${product.soldItem} Sold",
                    fontSize = 13.sp
                )
            }

            Text(
                modifier = Modifier
                    .clip(shape = Shapes.small)
                    .background(BackgroundBlueLight)
                    .padding(4.dp),
                text = product.tags,
                color = BackgroundMainWhite,
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
            )
        }
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
        BadgeNumberCart(badgeNumber, onCartClicked)
    }
    )
}

@Composable
fun AddToCart(price: String, isShowAddingToCartAnimation: Boolean, onAddToCart: () -> Unit) {
    val configuration = LocalConfiguration.current
    val fraction =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.17f else 0.085f
    Surface(
        modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight(fraction = fraction), color = BackgroundMainWhite
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier, colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (isShowAddingToCartAnimation) Color.Transparent
                    else BackgroundBlueLight
                ), onClick = onAddToCart
            ) {
                if (isShowAddingToCartAnimation) {
                    DotsTyping()
                } else {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.add_product_to_cart),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = BackgroundMainWhite
                        )
                    )
                }

            }
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(shape = Shapes.medium)
                    .background(BackgroundAddToCart)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                text = separateDigit(price),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = BackgroundMainBlack,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun DotsTyping() {

    val dotSize = 10.dp
    val delayUnit = 350
    val maxOffset = 10f

    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(y = -offset.dp)
            .background(
                color = BackgroundBlueLight,
                shape = CircleShape
            )
            .padding(start = 8.dp, end = 8.dp)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay with LinearEasing
                maxOffset at delay + delayUnit with LinearEasing
                0f at delay + delayUnit * 2
            }
        ), label = ""
    )

    val offset1 by animateOffsetWithDelay(0)
    val offset2 by animateOffsetWithDelay(delayUnit)
    val offset3 by animateOffsetWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        val spaceSize = 2.dp

        Dot(offset1)
        Spacer(Modifier.width(spaceSize))
        Dot(offset2)
        Spacer(Modifier.width(spaceSize))
        Dot(offset3)
    }
}

