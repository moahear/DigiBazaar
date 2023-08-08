package com.gamil.moahear.digibazaar.di

import com.gamil.moahear.digibazaar.data.repository.TokenInMemory
import com.gamil.moahear.digibazaar.data.source.ApiServices
import com.gamil.moahear.digibazaar.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseUrl=Constants.BASE_URL
const val networkTimeout=Constants.NETWORK_TIMEOUT

fun provideGson():Gson=GsonBuilder().setLenient().create()
fun provideInterceptor():HttpLoggingInterceptor=HttpLoggingInterceptor().apply {
    level=HttpLoggingInterceptor.Level.BODY
}
fun provideClient(timeout:Long,httpLoggingInterceptor: HttpLoggingInterceptor):OkHttpClient=OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .addInterceptor(Interceptor {chain->
        val oldRequest = chain.request()
        val newRequest = oldRequest.newBuilder()
        if (TokenInMemory.token !=null){
            newRequest.apply {
                addHeader("Authorization", TokenInMemory.token!!)
                addHeader("Accept","application/json")
                method(oldRequest.method,oldRequest.body)
            }
        }
        chain.proceed(newRequest.build())
    })
    .readTimeout(timeout,TimeUnit.SECONDS)
    .writeTimeout(timeout,TimeUnit.SECONDS)
    .connectTimeout(timeout,TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .pingInterval(3,TimeUnit.SECONDS)
    .build()

fun provideRetrofit(baseUrl:String,client: OkHttpClient,gson: Gson):ApiServices=Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build().create(ApiServices::class.java)