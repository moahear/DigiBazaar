package com.gamil.moahear.digibazaar.data.model


import com.google.gson.annotations.SerializedName

data class CartInfoResponse(
    @SerializedName("productList")
    val productList: List<Product>,
    @SerializedName("success")
    val success: Boolean, // true
    @SerializedName("totalPrice")
    val totalPrice: Int // 39420
) {
    data class Product(
        @SerializedName("category")
        val category: String, // Backpack
        @SerializedName("imgUrl")
        val imgUrl: String, // https://dunijet.ir/YaghootAndroidFiles/duniBazaar/louis.webp
        @SerializedName("name")
        val name: String, // Louis
        @SerializedName("price")
        val price: String, // 600
        @SerializedName("productId")
        val productId: String, // 13
        @SerializedName("quantity")
        val quantity: String, // 22
        @SerializedName("tags")
        val tags: String // Newest
    )
}