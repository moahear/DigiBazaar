package com.gamil.moahear.digibazaar.data.datastore

interface IDataStoreRepository {
    suspend fun loadToken()
    suspend fun saveToken(value:String)
    suspend fun getToken():String?
    suspend fun saveUsername(value:String)
    suspend fun getUserName():String?
    suspend fun clearDataStore()
}