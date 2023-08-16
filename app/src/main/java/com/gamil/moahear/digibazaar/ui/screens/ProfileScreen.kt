package com.gamil.moahear.digibazaar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gamil.moahear.digibazaar.R
import com.gamil.moahear.digibazaar.navigation.Screen
import com.gamil.moahear.digibazaar.ui.theme.BackgroundBlueLight
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.Shapes
import com.gamil.moahear.digibazaar.viewmodel.ProfileViewModel
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = koinInject(),
    onBackClickListener: () -> Unit,
    onNavigationToMainScreen: (String) -> Unit
) {
    val email by profileViewModel.email.collectAsStateWithLifecycle()
    val address by profileViewModel.address.collectAsStateWithLifecycle()
    val postalCode by profileViewModel.postalCode.collectAsStateWithLifecycle()
    val loginTime by profileViewModel.loginTime.collectAsStateWithLifecycle()
    val isShowDialog by profileViewModel.isShowDialog.collectAsStateWithLifecycle()
    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileToolbar(onBackClickListener = onBackClickListener)
            AvatarAnimation()
            Spacer(modifier = Modifier.height(6.dp))
            ShowUserInfo("Email", email, null)
            ShowUserInfo("Address", address) {
                profileViewModel.changeStateShowDialog(true)
            }
            ShowUserInfo("Postal code", postalCode) {
                profileViewModel.changeStateShowDialog(true)
            }
            ShowUserInfo("Login time", loginTime, null)

            Button(modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(fraction = 0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundBlueLight),
                onClick = {
                    profileViewModel.signOut()
                    onNavigationToMainScreen(Screen.MainScreen.route)
                }) {
                Text(
                    text = "Sign out", style = TextStyle(
                        color = BackgroundMainWhite
                    )
                )
            }
        }

        if (isShowDialog) {
            AddAddressDialog(isShowSaveToProfile = false, onDismissClickListener = {
                profileViewModel.showDialog(false)
            }, onSubmitClickListener = { address, postalCode, _ ->
                profileViewModel.saveAddress(address, postalCode)
            })
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileToolbar(onBackClickListener: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(text = "")
        },
        navigationIcon = {
            IconButton(onClick = onBackClickListener) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundMainWhite)
    )
}

@Composable
fun AvatarAnimation() {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.profile_anim))
    LottieAnimation(
        modifier = Modifier
            .padding(top = 0.dp, bottom = 16.dp)
            .size(150.dp),
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun ShowUserInfo(subject: String, text: String, onAddressClickListener: (() -> Unit)?) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clickable {
                onAddressClickListener?.invoke()
            }, horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = subject,
            style = TextStyle(
                fontSize = 18.sp,
                color = BackgroundBlueLight,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )

        Divider(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(), color = BackgroundBlueLight, thickness = 0.5.dp
        )
    }
}

@Composable
fun AddAddressDialog(
    isShowSaveToProfile: Boolean,
    onDismissClickListener: () -> Unit,
    onSubmitClickListener: (String, String, Boolean) -> Unit
) {
    val context = LocalContext.current
    var checkedState by remember {
        mutableStateOf(true)
    }
    var addressState by remember {
        mutableStateOf("")
    }
    var postalCodeState by remember {
        mutableStateOf("")
    }
    val fraction = if (isShowSaveToProfile) 0.695f else 0.625f

    Dialog(onDismissRequest = onDismissClickListener) {
        Card(modifier = Modifier.fillMaxHeight(fraction), shape = Shapes.medium) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Add address info",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))
                NormalTextField(
                    value = addressState,
                    imageVector = Icons.Default.LocationCity,
                    labelText = "Address",
                    hintText = "Enter your address",
                    onValueChange = {
                        addressState = it
                    }
                )

                NormalTextField(
                    value = postalCodeState,
                    imageVector = Icons.Default.AddLocation,
                    labelText = "Postal code",
                    hintText = "Enter your postal code",
                    onValueChange = {
                        postalCodeState = it
                    }
                )

                if (isShowSaveToProfile) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 4.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = checkedState, onCheckedChange = {
                            checkedState = it
                        })
                        Text(text = "Save to profile")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismissClickListener) {
                        Text(text = "Cancel", color = BackgroundBlueLight)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        if ((postalCodeState.isNotEmpty() || postalCodeState.isNotBlank()) &&
                            (addressState.isNotEmpty() || addressState.isNotBlank())
                        ) {
                            onSubmitClickListener(addressState, postalCodeState, checkedState)
                            onDismissClickListener.invoke()
                        } else {
                            Toast.makeText(
                                context,
                                "Please type address and postal code...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }) {
                        Text(text = "Ok", color = BackgroundBlueLight)
                    }
                }
            }
        }
    }
}