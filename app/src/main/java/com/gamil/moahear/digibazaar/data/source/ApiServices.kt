package com.gamil.moahear.digibazaar.data.source

import com.gamil.moahear.digibazaar.data.model.AddCommentResponse
import com.gamil.moahear.digibazaar.data.model.CommentsResponse
import com.gamil.moahear.digibazaar.data.model.LoginResponse
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @POST("signUp")
    suspend fun signUp(@Body jsonObject: JsonObject):Response<LoginResponse>
    @POST("signIn")
    suspend fun signIn(@Body jsonObject: JsonObject):Response<LoginResponse>

    @GET("refreshToken")
     fun refreshToken(): Call<LoginResponse>

     @GET("getProducts")
     suspend fun getAllProducts():Response<ProductsResponse>
     @GET("getSliderPics")
     suspend fun getAllAds():Response<SliderPicsResponse>

     @FormUrlEncoded
     @POST("getComments")
     suspend fun getComments(@Field(value = "productId") productId:String):Response<CommentsResponse>
    @FormUrlEncoded
     @POST("addNewComment")
     suspend fun addComment(@Field(value = "productId") productId:String,@Field(value = "text") message:String):Response<AddCommentResponse>
}