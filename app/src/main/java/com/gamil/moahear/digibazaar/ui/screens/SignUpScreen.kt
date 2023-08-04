package com.gamil.moahear.digibazaar.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlue
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.viewmodel.SignUpViewModel
import org.koin.compose.koinInject

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = koinInject(),
    onNavigate/*SignIn*/: (String) -> Unit
) {
    val context = LocalContext.current
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
                .fillMaxSize(0.95f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageTop()
            MainCard(signUpViewModel, onNavigate/*SignIn*/) {

                signUpViewModel.signUpUser {
                    if (it == "success") {
                        onNavigate/*SignIn*/(Screen.MainScreen.route)
                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun ImageTop() {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .size(64.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(0.dp)
                .background(BackgroundMainWhite),
            painter = painterResource(id = R.drawable.img_top_sign_up),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
    }
}

@Composable
fun MainCard(
    signUpViewModel: SignUpViewModel,
    onNavigateSignIn: (String) -> Unit,
    onSignUp: () -> Unit
) {
    val name by signUpViewModel.name.collectAsStateWithLifecycle()
    val email by signUpViewModel.email.collectAsStateWithLifecycle()
    val password by signUpViewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by signUpViewModel.confirmPassword.collectAsStateWithLifecycle()
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
                text = stringResource(id = R.string.sign_up),
                style = TextStyle(color = BackgroundBlue),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            NormalTextField(
                value = name,
                imageVector = Icons.Filled.Person,
                labelText = "Full name",
                hintText = "Enter full name", onValueChange = signUpViewModel::setName
            )

            NormalTextField(
                value = email,
                imageVector = Icons.Filled.Email,
                labelText = "Email Address",
                hintText = "Enter your email", onValueChange = signUpViewModel::setEmail
            )

            PasswordTextField(
                value = password,
                imageVector = Icons.Outlined.Lock,
                labelText = "Password",
                hintText = "Enter password", onValueChange = signUpViewModel::setPassword
            )

            PasswordTextField(
                value = confirmPassword,
                imageVector = Icons.Outlined.Lock,
                labelText = "Confirm password",
                hintText = "Enter confirm password",
                onValueChange = signUpViewModel::setConfirmPassword


            )

            Button(modifier = Modifier.padding(top = 28.dp, bottom = 8.dp), onClick = {


                onSignUp.invoke()
            }) {
                Text(modifier = Modifier.padding(8.dp), text = "Register Account")
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 18.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Already have an account?")
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onNavigateSignIn(Screen.SignInScreen.route) }) {
                    Text(text = "Log In", color = BackgroundBlue)
                }
            }
        }
    }
}

@Composable
fun NormalTextField(
    value: String,
    imageVector: ImageVector,
    labelText: String,
    /*supportingText: @Composable() (() -> Unit)? = null,
    isError: Boolean = false,*/
    hintText: String, onValueChange: (String) -> Unit
) {
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth(0.9f)
        .padding(12.dp), value = value, onValueChange = onValueChange, label = {
        Text(text = labelText)
    }, singleLine = true, placeholder = { Text(text = hintText) }, shape = Shapes.medium,
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = null)
        }
    )
}

@Composable
fun PasswordTextField(
    value: String,
    imageVector: ImageVector,
    labelText: String,
    hintText: String, onValueChange: (String) -> Unit
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth(0.9f)
        .padding(12.dp), value = value, onValueChange = onValueChange, label = {
        Text(text = labelText)
    }, singleLine = true, placeholder = { Text(text = hintText) }, shape = Shapes.medium,
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = null)
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                if (passwordVisibility) Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = null
                ) else Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = null)
            }
        }
    )
}