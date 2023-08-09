package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gamil.moahear.digibazaar.ui.components.CategoryTop
import com.gamil.moahear.digibazaar.ui.components.Products
import com.gamil.moahear.digibazaar.ui.components.TopToolBar
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlue
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.utils.Constants
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(16.dp)
    ) {

        if (mainViewModel.isShownProgressBar.collectAsStateWithLifecycle().value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = BackgroundBlue)
        }
        TopToolBar() {
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
}


