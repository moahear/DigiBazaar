package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.datastore.IDataStoreRepository
import com.gamil.moahear.digibazaar.data.repository.user.IUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val dataStoreRepository: IDataStoreRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email.asStateFlow()


    private val _isShowDialog = MutableStateFlow(false)
    val isShowDialog: StateFlow<Boolean>
        get() = _isShowDialog.asStateFlow()


    private var _address = MutableStateFlow("")
    val address: StateFlow<String>
        get() = _address.asStateFlow()


    private val _postalCode = MutableStateFlow("")
    val postalCode: StateFlow<String>
        get() = _postalCode.asStateFlow()


    private val _loginTime = MutableStateFlow("")
    val loginTime: StateFlow<String>
        get() = _loginTime.asStateFlow()

    fun getUserData() {
        viewModelScope.launch {
            _email.value = dataStoreRepository.getUserName().toString()
            _loginTime.value = dataStoreRepository.getLoginTime()
            _postalCode.value = dataStoreRepository.getPostalCode()
            _address.value = dataStoreRepository.getAddress()
        }
    }

    fun changeStateShowDialog(isVisible: Boolean) {
        _isShowDialog.value = isVisible
    }

    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
        }
    }

    fun saveAddress(address: String, postalCode: String) {

        viewModelScope.launch {
            dataStoreRepository.saveAddress(address)
            dataStoreRepository.savePostalCode(postalCode)
            _address.value = address
            _postalCode.value = postalCode
        }
    }

    fun showDialog(isShowDialog: Boolean) {
        _isShowDialog.value = isShowDialog
    }

    init {
        getUserData()
    }
}