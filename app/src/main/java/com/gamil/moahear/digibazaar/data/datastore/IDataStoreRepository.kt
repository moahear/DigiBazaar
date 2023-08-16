package com.gamil.moahear.digibazaar.data.datastore

interface IDataStoreRepository {
    suspend fun loadToken()
    suspend fun saveToken(value: String)
    suspend fun getToken(): String?

    suspend fun saveAddress(value: String)
    suspend fun getAddress(): String
    suspend fun saveLoginTime()
    suspend fun getLoginTime(): String


    suspend fun savePostalCode(value: String)
    suspend fun getPostalCode(): String

    suspend fun saveUsername(value: String)
    suspend fun getUserName(): String?
    suspend fun clearDataStore()
}