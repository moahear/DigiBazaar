package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val productRepository: IProductRepository) : ViewModel() {
    private val _productsByCategoryName = MutableStateFlow<List<ProductsResponse.Product>>(listOf())
    val productsByCategoryName: StateFlow<List<ProductsResponse.Product>>
        get() = _productsByCategoryName.asStateFlow()

    fun getProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            _productsByCategoryName.value = productRepository.getProductsByCategory(categoryName)
            delay(5000)
        }
    }
}