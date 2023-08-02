package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel:ViewModel() {
    private var _email= MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email.asStateFlow()
    fun setEmail(email:String){
        _email.value=email
    }

    private var _password= MutableStateFlow("")
    val password: StateFlow<String>
        get() = _password.asStateFlow()
    fun setPassword(password:String){
        _password.value=password
    }

    fun signInUser() {
    }
}