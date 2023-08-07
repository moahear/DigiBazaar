package com.gamil.moahear.digibazaar.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gamil.moahear.digibazaar.utils.Constants
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("success")
    val success: Boolean // true
) {
    @Entity(tableName = Constants.PRODUCT_TABLE)
    data class Product(
        @SerializedName("category")
        val category: String, // Handbag
        @SerializedName("detailText")
        val detailText: String, // This bag is a leather bag with gold straps that is suitable for women who want to have luxury and beautiful bags.
        @SerializedName("imgUrl")
        val imgUrl: String, // https://dunijet.ir/YaghootAndroidFiles/duniBazaar/golden_strap.webp
        @SerializedName("material")
        val material: String, // Leather
        @SerializedName("name")
        val name: String, // Golden strap
        @SerializedName("price")
        val price: String, // 800
        @SerializedName("productId")
        @PrimaryKey
        val productId: String, // 2
        @SerializedName("soldItem")
        val soldItem: String, // 35
        @SerializedName("tags")
        val tags: String // Best Sellers
    )
}