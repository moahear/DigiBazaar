package com.gamil.moahear.digibazaar.data.repository.cart

import com.gamil.moahear.digibazaar.data.source.ApiServices

class CartRepositoryImpl(private val apiServices: ApiServices) : ICartRepository {
    override suspend fun addToCart(productId: String): Boolean {
        val data = apiServices.addToCart(productId).body()
        if (data != null) {
            return data.success
        }
        return false
    }
}