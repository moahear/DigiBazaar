package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.repository.user.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: IUserRepository) : ViewModel() {
    private var _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    private var _password = MutableStateFlow("")
    val password: StateFlow<String>
        get() = _password.asStateFlow()

    fun setPassword(password: String) {
        _password.value = password
    }

    fun signInUser(onSignInResult: (String) -> Unit) {
        viewModelScope.launch {
            onSignInResult(userRepository.signIn(email.value, password.value))
        }
    }
}