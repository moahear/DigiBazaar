package com.gamil.moahear.digibazaar.data.repository.cart

import com.gamil.moahear.digibazaar.data.model.CartInfoResponse
import com.gamil.moahear.digibazaar.data.source.ApiServices

class CartRepositoryImpl(private val apiServices: ApiServices) : ICartRepository {
    override suspend fun addToCart(productId: String): Boolean {
        val data = apiServices.addToCart(productId).body()
        if (data != null) {
            return data.success
        }
        return false
    }

    override suspend fun removeFromCart(productId: String): Boolean {
        val data = apiServices.removeFromCart(productId).body()
        if (data != null) {
            return data.success
        }
        return false
    }

    override suspend fun getCartInfo(): CartInfoResponse? {
        apiServices.getUserCart().body()
        return apiServices.getUserCart().body()
    }

    override suspend fun getCountInCart(): Int {
        val data = apiServices.getUserCart().body()
        if (data?.success == true) return data.productList.sumOf {
            it.quantity.toInt()
        }
        return 0
    }
}



