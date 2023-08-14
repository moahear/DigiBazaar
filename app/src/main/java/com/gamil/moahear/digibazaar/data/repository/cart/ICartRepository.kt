package com.gamil.moahear.digibazaar.data.repository.cart


interface ICartRepository {
    suspend fun addToCart(productId:String):Boolean
}