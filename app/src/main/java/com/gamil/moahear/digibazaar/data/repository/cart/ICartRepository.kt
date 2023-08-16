package com.gamil.moahear.digibazaar.data.repository.cart

import com.gamil.moahear.digibazaar.data.model.CartInfoResponse


interface ICartRepository {
    suspend fun addToCart(productId: String): Boolean
    suspend fun removeFromCart(productId: String): Boolean
    suspend fun getCartInfo(): CartInfoResponse?
    suspend fun getCountInCart(): Int
}