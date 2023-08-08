package com.gamil.moahear.digibazaar.utils

import com.gamil.moahear.digibazaar.R

object Constants {
    const val INTRO_SCREEN = "intro_screen"
    const val SIGN_IN_SCREEN = "sign_in_screen"
    const val SIGN_UP_SCREEN = "sign_up_screen"
    const val CART_SCREEN = "cart_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val CATEGORY_SCREEN = "category_screen"
    const val PRODUCT_SCREEN = "product_screen"
    const val MAIN_SCREEN = "main_screen"
    const val NO_INTERNET_SCREEN = "no_internet_screen"
    const val KEY_PRODUCT_ARG = "product_id"
    const val KEY_CATEGORY_ARG = "category_name"
    const val DATA_STORE_NAME = "digi_bazaar_data_store"
    const val BASE_URL = "https://dunijet.ir/Projects/DuniBazaar/"
    const val NETWORK_TIMEOUT = 60L
    const val PRODUCT_TABLE = "product_table"
    const val APP_DATABASE = "app_database.db"
    val CATEGORIES = listOf(
        Pair("Backpack", R.drawable.ic_cat_backpack),
        Pair("Handbag", R.drawable.ic_cat_handbag),
        Pair("Shopping", R.drawable.ic_cat_shopping),
        Pair("Tote", R.drawable.ic_cat_tote),
        Pair("Satchel", R.drawable.ic_cat_satchel),
        Pair("Clutch", R.drawable.ic_cat_clutch),
        Pair("Wallet", R.drawable.ic_cat_wallet),
        Pair("Sling", R.drawable.ic_cat_sling),
        Pair("Bucket", R.drawable.ic_cat_bucket),
        Pair("Quilted", R.drawable.ic_cat_quilted)
    )
    val TAGS = listOf(
        "Newest",
        "Best Sellers",
        "Most Visited",
        "Highest Quality"
    )
}