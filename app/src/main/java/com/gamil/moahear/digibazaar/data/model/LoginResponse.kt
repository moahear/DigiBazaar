package com.gamil.moahear.digibazaar.data.model

data class LoginResponse(
    val expiresAt:Int,
    val message:String,
    val success:Boolean,
    val token:String,

)
