package com.gamil.moahear.digibazaar.utils

import com.gamil.moahear.digibazaar.data.repository.TokenInMemory
import com.gamil.moahear.digibazaar.data.source.ApiServices
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthenticationChecker(private val apiServices: ApiServices) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (TokenInMemory.token != null && !response.request.url.pathSegments.last()
                .equals("refreshToken", false)
        ) {
            if (refreshToken() == true) {
                response.request
            }
        }
        return null
    }

    private fun refreshToken(): Boolean? {
        val response = apiServices.refreshToken().execute()
        return response.body()?.success
    }
}