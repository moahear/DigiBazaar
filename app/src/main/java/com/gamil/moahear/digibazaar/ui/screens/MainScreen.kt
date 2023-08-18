package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.data.model.CheckoutResponse
import com.gamil.moahear.digibazaar.ui.components.CategoryTop
import com.gamil.moahear.digibazaar.ui.components.Products
import com.gamil.moahear.digibazaar.ui.components.TopToolBar
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlue
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.utils.Constants
import com.gamil.moahear.digibazaar.utils.separateDigit
import com.gamil.moahear.digibazaar.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinInject(parameters = {
        parametersOf(true)
    }), onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    //Change status bar color
    val uiSystemUiController = rememberSystemUiController()
    SideEffect {
        uiSystemUiController.setStatusBarColor(BackgroundMainWhite)
    }
    val isShownPaymentResultDialog by mainViewModel.isShownPaymentResultDialog.collectAsStateWithLifecycle()
    val paymentStatus by mainViewModel.paymentStatus.collectAsStateWithLifecycle()
    if (paymentStatus == Constants.PAYMENT_PENDING) {
        //check internet later
        mainViewModel.getCheckoutInfo()
    }
    mainViewModel.getPaymentStatus()
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp)
        ) {

            if (mainViewModel.isShownProgressBar.collectAsStateWithLifecycle().value) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = BackgroundBlue)
            }
            TopToolBar(mainViewModel.badgeNumber.collectAsStateWithLifecycle().value) {
                onNavigate(it)
            }
            CategoryTop(Constants.CATEGORIES) { category ->
                onNavigate(category)
            }
            val products by mainViewModel.products.collectAsStateWithLifecycle()
            val ads by mainViewModel.ads.collectAsStateWithLifecycle()
            Products(Constants.TAGS, products, ads) { product ->
                onNavigate(product)
            }
        }
        if (paymentStatus == Constants.PAYMENT_PENDING && isShownPaymentResultDialog) {
            PaymentResultDialog(checkoutResult =
            mainViewModel.checkoutInfo.collectAsStateWithLifecycle().value,
                onDismissListener = {
                    mainViewModel.setPaymentStatus(Constants.NO_PAYMENT)
                    mainViewModel.setIsShownPaymentResultDialog(false)
                })
        }
    }

}

@Composable
fun PaymentResultDialog(checkoutResult: CheckoutResponse, onDismissListener: () -> Unit) {
    Dialog(onDismissRequest = onDismissListener) {
        Card(shape = Shapes.medium) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Payment result",
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (checkoutResult.order?.status?.toInt() == Constants.PAYMENT_SUCCESS) {
                    AsyncImage(
                        model = R.drawable.success_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(110.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Payment was successful", style = TextStyle(fontSize = 16.sp))
                    Text(text = "Purchase amount: ${separateDigit(checkoutResult.order.amount)}")
                } else {
                    AsyncImage(
                        model = R.drawable.fail_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(110.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Payment was not successful", style = TextStyle(fontSize = 16.sp))
                    Text(
                        text = "Purchase amount: ${
                            checkoutResult.order?.amount?.let {
                                separateDigit(
                                    it
                                )
                            }
                        }"
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissListener) {
                        Text(text = "Ok")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

            }
        }
    }
}


