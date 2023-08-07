package com.gamil.moahear.digibazaar.data.repository.user

import com.gamil.moahear.digibazaar.data.datastore.IDataStoreRepository
import com.gamil.moahear.digibazaar.data.repository.TokenInMemory
import com.gamil.moahear.digibazaar.data.source.ApiServices
import com.google.gson.JsonObject

class UserRepositoryImpl(
    private val dataStore: IDataStoreRepository,
    private val apiServices: ApiServices
) : IUserRepository {

    override suspend fun signUp(name: String, userName: String, password: String): String {
        val jsonObject = JsonObject().apply {
            addProperty("name", name)
            addProperty("email", userName)
            addProperty("password", password)
        }
        val result = apiServices.signUp(jsonObject)
        return if (result.isSuccessful) {
            result.body()?.let {
                TokenInMemory.refreshToken(userName, it.token)
                dataStore.saveToken(it.token)
                dataStore.saveUsername(userName)
            }

            "success"
        } else {
            result.message()
        }
    }

    override suspend fun signIn(userName: String, password: String): String {
        val jsonObject = JsonObject().apply {
            addProperty("email", userName)
            addProperty("password", password)
        }
        val result = apiServices.signIn(jsonObject)
        if (result.isSuccessful) {
            result.body()?.let {
                if (it.success) {
                    TokenInMemory.refreshToken(userName, it.token)
                    dataStore.saveToken(it.token)
                    dataStore.saveUsername(userName)
                    return "success"
                }
                else return  result.message()
            }
             return result.message()
        }
        else return result.message()
    }

    override suspend fun signOut() {
        TokenInMemory.refreshToken(null, null)
        dataStore.clearDataStore()
    }

}