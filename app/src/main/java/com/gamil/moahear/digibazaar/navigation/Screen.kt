package com.gamil.moahear.digibazaar.navigation

import com.gamil.moahear.digibazaar.utils.Constants

sealed class Screen(val route: String) {
    data object MainScreen: Screen(Constants.MAIN_SCREEN)
    data object ProductScreen: Screen(Constants.PRODUCT_SCREEN)
    data object CategoryScreen: Screen(Constants.CATEGORY_SCREEN)
    data object ProfileScreen: Screen(Constants.PROFILE_SCREEN)
    data object CartScreen: Screen(Constants.CART_SCREEN)
    data object SignUpScreen: Screen(Constants.SIGN_UP_SCREEN)
    data object SignInScreen: Screen(Constants.SIGN_IN_SCREEN)
    data object NoInternetScreen: Screen(Constants.NO_INTERNET_SCREEN)
    fun withArgs(vararg args:Any):String{
        return buildString{
            append(route)
            args.forEach {
                append("/{$it}")
            }
        }
    }
}