package com.gamil.moahear.digibazaar.data.repository

object TokenInMemory {
    var token: String? = null
        private set
    var userName: String? = null
        private set

    fun refreshToken(
        userName: String?,
        newToken: String?/*, address: String?,postalCode: String?*/
    ) {
        this.token = newToken
        this.userName = userName
    }
}