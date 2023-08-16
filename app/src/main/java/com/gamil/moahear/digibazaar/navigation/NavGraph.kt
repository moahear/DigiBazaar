package com.gamil.moahear.digibazaar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gamil.moahear.digibazaar.data.repository.TokenInMemory
import com.gamil.moahear.digibazaar.ui.screens.CartScreen
import com.gamil.moahear.digibazaar.ui.screens.CategoryScreen
import com.gamil.moahear.digibazaar.ui.screens.IntroScreen
import com.gamil.moahear.digibazaar.ui.screens.MainScreen
import com.gamil.moahear.digibazaar.ui.screens.ProductScreen
import com.gamil.moahear.digibazaar.ui.screens.ProfileScreen
import com.gamil.moahear.digibazaar.ui.screens.SignInScreen
import com.gamil.moahear.digibazaar.ui.screens.SignUpScreen
import com.gamil.moahear.digibazaar.utils.Constants

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            if (TokenInMemory.token != null) {
                MainScreen() {
                    navHostController.navigate(it)
                }
            } else {
                IntroScreen() {
                    navHostController.navigate(it)
                }
            }

        }

        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(onBackClickListener = { navHostController.popBackStack() }) {
                navHostController.navigate(it) {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Screen.SignInScreen.route) {
            SignInScreen() {
                navHostController.navigate(it) {
                    popUpTo(route = Screen.MainScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen() {
                navHostController.navigate(it) {
                    popUpTo(route = Screen.MainScreen.route) {
                        inclusive = true
                    }
                }

            }
        }
        composable(route = Screen.CartScreen.route) {
            CartScreen(
                onBackClicked = { navHostController.popBackStack() }
            ) {
                navHostController.navigate(it)
            }
        }
        composable(route = Screen.NoInternetScreen.route) {

        }
        composable(route = Screen.CategoryScreen.withArgs(Constants.KEY_CATEGORY_ARG),
            arguments = listOf(
                navArgument(Constants.KEY_CATEGORY_ARG) { type = NavType.StringType }
            )) {
            it.arguments?.let { it1 ->
                CategoryScreen(
                    categoryName = it1.getString(Constants.KEY_CATEGORY_ARG, "").drop(1).dropLast(1)
                ) { route ->
                    navHostController.navigate(route)
                }
            }
        }
        composable(route = Screen.ProductScreen.withArgs(Constants.KEY_PRODUCT_ARG),
            arguments = listOf(
                navArgument(Constants.KEY_PRODUCT_ARG) { type = NavType.StringType }
            )) {
            it.arguments?.let { it1 ->
                ProductScreen(
                    productId = it1.getString(Constants.KEY_PRODUCT_ARG, "").dropLast(1).drop(1),
                    onBackClicked = {
                        navHostController.popBackStack()
                    },
                    onCartClicked = {
                        //if  check internet
                        navHostController.navigate(Screen.CartScreen.route)
                        // else

                    }) { route ->
                    navHostController.navigate(route)
                }
            }

        }
    }
}