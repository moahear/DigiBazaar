package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import com.gamil.moahear.digibazaar.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: IProductRepository) : ViewModel() {
    private val _product = MutableStateFlow(Constants.PRODUCT_EMPTY)
    val product: StateFlow<ProductsResponse.Product>
        get() = _product.asStateFlow()

    fun getProduct(productId: String) {
        getProductFromCache(productId)
    }

    private fun getProductFromCache(productId: String) {
        viewModelScope.launch() {
            _product.value = productRepository.getProductById(productId)
        }
    }
}