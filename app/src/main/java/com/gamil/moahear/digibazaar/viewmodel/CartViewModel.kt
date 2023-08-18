package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.datastore.IDataStoreRepository
import com.gamil.moahear.digibazaar.data.model.CartInfoResponse
import com.gamil.moahear.digibazaar.data.repository.cart.ICartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: ICartRepository,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel() {
    private var _cartSize = MutableStateFlow(-1)
    val cartSize: StateFlow<Int>
        get() = _cartSize

    private var _address = MutableStateFlow("")
    val address: StateFlow<String>
        get() = _address.asStateFlow()

    private var _postalCode = MutableStateFlow("")
    val postalCode: StateFlow<String>
        get() = _postalCode.asStateFlow()

    private var _productSInCart = MutableStateFlow<List<CartInfoResponse.Product>>(emptyList())
    val productSInCart: StateFlow<List<CartInfoResponse.Product>>
        get() = _productSInCart.asStateFlow()

    private var _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int>
        get() = _totalPrice.asStateFlow()


    private var _isChangingNumber = MutableStateFlow(Pair("", false))
    val isChangingNumber: StateFlow<Pair<String, Boolean>>
        get() = _isChangingNumber.asStateFlow()


    private fun getCartInfo() {
        viewModelScope.launch {
            cartRepository.getCartInfo()?.let {
                _productSInCart.value = it.productList
                _totalPrice.value = it.totalPrice
            }
            _cartSize.value = cartRepository.getCountInCart()
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            _isChangingNumber.value = _isChangingNumber.value.copy(productId, true)
            val isSuccess = cartRepository.removeFromCart(productId)
            if (isSuccess) {
                getCartInfo()
            }
            delay(100)
            _isChangingNumber.value = _isChangingNumber.value.copy(productId, false)

        }
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            _isChangingNumber.value = _isChangingNumber.value.copy(productId, true)
            val isSuccess = cartRepository.addToCart(productId)
            if (isSuccess) {
                getCartInfo()
            }
            delay(100)
            _isChangingNumber.value = _isChangingNumber.value.copy(productId, false)

        }
    }

    fun saveAddress(address: String, postalCode: String) {
        viewModelScope.launch {
            dataStoreRepository.saveAddress(address)
            dataStoreRepository.savePostalCode(postalCode)
        }
    }

    private fun getAddress() {
        viewModelScope.launch {
            _address.value = dataStoreRepository.getAddress()
            _postalCode.value = dataStoreRepository.getPostalCode()
        }
    }

    fun purchase(
        address: String,
        postalCode: String,
        onPurchaseClickListener: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            val result = cartRepository.submitOrder(address, postalCode)
            if (result != null) {
                onPurchaseClickListener(result.success, result.paymentLink)
                dataStoreRepository.saveOrderId(result.orderId.toString())
            }

        }
    }

    fun savePaymentStatus(status: Int) {
        viewModelScope.launch {
            dataStoreRepository.savePaymentStatus(status)
        }
    }

    init {
        getCartInfo()
        getAddress()
    }
}