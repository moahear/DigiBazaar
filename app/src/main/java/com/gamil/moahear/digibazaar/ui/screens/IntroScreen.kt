package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlue
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlueLight
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite

@Composable
fun IntroScreen(onNavigate: (String) -> Unit) {
    Image(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue),
        painter = painterResource(id = R.drawable.img_bags_intro),
        contentDescription = null,
        contentScale = ContentScale.FillHeight
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.78f),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(width = 0.002.dp, color = BackgroundBlueLight),
            onClick = { onNavigate(Screen.SignUpScreen.route) }) {
            Text(
                text = stringResource(R.string.sign_up), color = BackgroundMainWhite
            )
        }
        Button(modifier = Modifier.fillMaxWidth(0.7f),
            border = BorderStroke(width = 0.002.dp, color = BackgroundMainWhite),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = { onNavigate(Screen.SignInScreen.route) }) {
            Text(
                text = stringResource(R.string.sign_in), color = BackgroundBlueLight
            )
        }
    }
}