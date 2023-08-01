package com.gamil.moahear.digibazaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gamil.moahear.digibazaar.navigation.SetUpNavGraph
import com.gamil.moahear.digibazaar.ui.theme.BackgroundMainWhite
import com.gamil.moahear.digibazaar.ui.theme.DigiBazaarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = BackgroundMainWhite, modifier = Modifier.fillMaxSize()) {
                DigiBazaarTheme {
                    //region ui
                    DigiBazaarUi()
                    //endregion
                }
            }
        }
    }
}

//region ui
@Composable
fun DigiBazaarUi() {
    val navHostController = rememberNavController()
    SetUpNavGraph(navHostController)
}
//endregion