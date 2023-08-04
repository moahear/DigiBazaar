package com.gamil.moahear.digibazaar.data.repository.user

interface UserRepository {
    suspend fun signUp(name: String, userName: String, password: String): String
    suspend fun signIn(userName: String, password: String): String
    suspend fun signOut()
}