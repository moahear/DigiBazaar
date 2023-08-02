package com.gamil.moahear.digibazaar.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel:ViewModel() {
    private var _name=MutableStateFlow("")
    val name:StateFlow<String>
        get() = _name.asStateFlow()
    fun setName(name:String){
        _name.value=name
    }

    private var _email=MutableStateFlow("")
    val email:StateFlow<String>
        get() = _email.asStateFlow()
    fun setEmail(email:String){
        _email.value=email
    }

    private var _password=MutableStateFlow("")
    val password:StateFlow<String>
        get() = _password.asStateFlow()
    fun setPassword(password:String){
        _password.value=password
    }

    private var _confirmPassword=MutableStateFlow("")
    val confirmPassword:StateFlow<String>
        get() = _confirmPassword.asStateFlow()
    fun setConfirmPassword(confirmPassword:String){
        _confirmPassword.value=confirmPassword
    }

    fun signUpUser() {
        Log.i("0000", "signUpUser: ")
    }

}