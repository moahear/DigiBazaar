package com.gamil.moahear.digibazaar.data.repository.cart

import com.gamil.moahear.digibazaar.data.model.CartInfoResponse
import com.gamil.moahear.digibazaar.data.model.CheckoutResponse
import com.gamil.moahear.digibazaar.data.model.SubmitOrderResponse


interface ICartRepository {
    suspend fun addToCart(productId: String): Boolean
    suspend fun removeFromCart(productId: String): Boolean
    suspend fun getCartInfo(): CartInfoResponse?
    suspend fun getCountInCart(): Int

    suspend fun submitOrder(address: String, postalCode: String): SubmitOrderResponse?
    suspend fun checkout(orderId: String): CheckoutResponse?
}