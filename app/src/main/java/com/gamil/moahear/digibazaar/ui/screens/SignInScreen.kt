package com.gamil.moahear.digibazaar.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlue
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.viewmodel.SignInViewModel
import org.koin.compose.koinInject

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = koinInject(),
    onNavigateSignIn: (String) -> Unit
) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            painter = ColorPainter(BackgroundBlue),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageTop()
            SingInCard(signInViewModel, onNavigateSignIn) { signInViewModel.signInUser() }
        }
    }
}

@Composable
fun SingInCard(
    signInViewModel: SignInViewModel,
    onNavigateSignIn: (String) -> Unit,
    onSignUp: () -> Unit
) {
    val email by signInViewModel.email.collectAsStateWithLifecycle()
    val password by signInViewModel.password.collectAsStateWithLifecycle()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        colors = CardDefaults.cardColors(containerColor = BackgroundMainWhite),
        border = BorderStroke(width = 0.02.dp, color = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = Shapes.medium
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                text = stringResource(id = R.string.sign_in),
                style = TextStyle(color = BackgroundBlue),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            NormalTextField(
                value = email,
                imageVector = Icons.Filled.Email,
                labelText = stringResource(R.string.email_address),
                hintText = stringResource(R.string.enter_your_email), onValueChange = signInViewModel::setEmail
            )
            PasswordTextField(
                value = password,
                imageVector = Icons.Outlined.Lock,
                labelText = stringResource(R.string.password),
                hintText = stringResource(R.string.enter_password), onValueChange = signInViewModel::setPassword
            )


            Button(modifier = Modifier.padding(top = 28.dp, bottom = 8.dp), onClick = onSignUp) {
                Text(modifier = Modifier.padding(8.dp), text = "Log In")
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 18.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.don_t_have_an_account))
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onNavigateSignIn(Screen.SignUpScreen.route) }) {
                    Text(text = stringResource(R.string.register_now), color = BackgroundBlue)
                }
            }
        }
    }
}

