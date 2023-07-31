package com.gamil.moahear.digibazaar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.gamil.moahear.digibazaar.ui.theme.DigiBazaarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigiBazaarTheme {
                //region ui
                DigiBazaarUi()
                //endregion
            }
        }
    }
}


//region ui
@Composable
private fun DigiBazaarUi() {

}
//endregion