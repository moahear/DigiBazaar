package com.gamil.moahear.digibazaar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gamil.moahear.digibazaar.ui.screens.IntroScreen
import com.gamil.moahear.digibazaar.ui.screens.SignInScreen
import com.gamil.moahear.digibazaar.ui.screens.SignUpScreen
import com.gamil.moahear.digibazaar.utils.Constants

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.IntroScreen.route) {
        composable(route = Screen.IntroScreen.route) {
            IntroScreen {
                navHostController.navigate(it)
            }


        }

        composable(route = Screen.MainScreen.route) {

        }

        composable(route = Screen.ProfileScreen.route) {

        }

        composable(route = Screen.SignInScreen.route) {
            SignInScreen() {
                navHostController.navigate(it) {
                    popUpTo(route = Screen.SignInScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen() {
                navHostController.navigate(it) {
                    popUpTo(route = Screen.SignUpScreen.route) {
                        inclusive = true
                    }
                }

            }
        }

        composable(route = Screen.CartScreen.route) {

        }

        composable(route = Screen.NoInternetScreen.route) {

        }


        composable(route = Screen.CategoryScreen.withArgs(Constants.KEY_CATEGORY_ARG),
            arguments = listOf(
                navArgument(Constants.KEY_CATEGORY_ARG) { type = NavType.StringType }
            )) {

        }
        composable(route = Screen.ProductScreen.withArgs(Constants.KEY_PRODUCT_ARG),
            arguments = listOf(
                navArgument(Constants.KEY_PRODUCT_ARG) { type = NavType.StringType }
            )) {

        }


    }
}