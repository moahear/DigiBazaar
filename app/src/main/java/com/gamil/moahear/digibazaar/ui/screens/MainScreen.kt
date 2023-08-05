package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gamil.moahear.digibazaar.ui.components.CategoryTop
import com.gamil.moahear.digibazaar.ui.components.TopToolBar
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen() {
    //Change status bar color
    val uiSystemUiController = rememberSystemUiController()
    SideEffect {
        uiSystemUiController.setStatusBarColor(BackgroundMainWhite)
    }
    Column(modifier= Modifier
        .fillMaxSize()
        .verticalScroll(state = rememberScrollState()).padding(16.dp)) {
        TopToolBar()
        CategoryTop()
        ProductSubject()
        ProductSubject()
        ImageAdvertising()
        ProductSubject()
        ProductSubject()

    }

}



@Composable
fun ImageAdvertising() {

}

@Composable
fun ProductSubject() {

}


