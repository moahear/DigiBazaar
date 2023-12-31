package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.datastore.IDataStoreRepository
import com.gamil.moahear.digibazaar.data.model.CheckoutResponse
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse
import com.gamil.moahear.digibazaar.data.repository.cart.ICartRepository
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import com.gamil.moahear.digibazaar.utils.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val dataStoreRepository: IDataStoreRepository,
    private val productRepository: IProductRepository,
    private val cartRepository: ICartRepository,
    isInternetConnected: Boolean
) : ViewModel() {
    private val _isShownProgressBar = MutableStateFlow(false)
    val isShownProgressBar: StateFlow<Boolean>
        get() = _isShownProgressBar.asStateFlow()


    private val _isShownPaymentResultDialog = MutableStateFlow(false)
    val isShownPaymentResultDialog: StateFlow<Boolean>
        get() = _isShownPaymentResultDialog.asStateFlow()


    private val _products = MutableStateFlow<List<ProductsResponse.Product>>(emptyList())
    val products: StateFlow<List<ProductsResponse.Product>>
        get() = _products.asStateFlow()


    private val _paymentStatus = MutableStateFlow(Constants.NO_PAYMENT)
    val paymentStatus: StateFlow<Int>
        get() = _paymentStatus.asStateFlow()


    private val _checkoutInfo = MutableStateFlow(CheckoutResponse(null, null))
    val checkoutInfo: StateFlow<CheckoutResponse>
        get() = _checkoutInfo.asStateFlow()


    private val _ads = MutableStateFlow<List<SliderPicsResponse.Ad>>(emptyList())
    val ads: StateFlow<List<SliderPicsResponse.Ad>>
        get() = _ads.asStateFlow()

    init {
        refreshAllDataFromInternet(isInternetConnected)
    }

    private val _badgeNumber = MutableStateFlow(0)
    val badgeNumber: StateFlow<Int>
        get() = _badgeNumber.asStateFlow()

    private fun refreshAllDataFromInternet(hasInternet: Boolean) {
        viewModelScope.launch {
            if (hasInternet) _isShownProgressBar.value = true

            val newProducts = async {
                productRepository.getAllProducts(hasInternet)
            }
            val newAds = async {
                productRepository.getAllAds(hasInternet)
            }
            val badgeCount = async {
                cartRepository.getCountInCart()
            }
            updateData(newProducts.await(), badgeCount.await(), newAds.await())
            _isShownProgressBar.value = false
        }
    }

    private fun updateData(
        products: List<ProductsResponse.Product>,
        badgeCount: Int,
        ads: List<SliderPicsResponse.Ad>
    ) {
        _products.value = products
        _ads.value = ads
        _badgeNumber.value = badgeCount
    }

    fun getPaymentStatus() {
        viewModelScope.launch() {
            _paymentStatus.value = dataStoreRepository.getPurchaseStatus()
        }
    }

    fun setPaymentStatus(status: Int) {
        viewModelScope.launch {
            dataStoreRepository.savePaymentStatus(status)
        }
    }

    fun setIsShownPaymentResultDialog(isShown: Boolean) {
        _isShownPaymentResultDialog.value = isShown
    }

    fun getCheckoutInfo() {
        viewModelScope.launch {
            val result = cartRepository.checkout(dataStoreRepository.getOrderId())
            result?.let {
                if (it.success == true) {
                    _checkoutInfo.value = result
                    _isShownPaymentResultDialog.value = true
                }
            }
        }
    }
}