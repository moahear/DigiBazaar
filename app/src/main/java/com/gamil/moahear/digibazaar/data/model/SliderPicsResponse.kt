package com.gamil.moahear.digibazaar.data.model


import com.google.gson.annotations.SerializedName

data class SliderPicsResponse(
    @SerializedName("ads")
    val ads: List<Ad>,
    @SerializedName("success")
    val success: Boolean // true
) {
    data class Ad(
        @SerializedName("adId")
        val adId: String, // 2
        @SerializedName("imageURL")
        val imageURL: String, // https://dunijet.ir/YaghootAndroidFiles/duniBazaar/ads/crossBody_cover.webp
        @SerializedName("productId")
        val productId: String // 15
    )
}