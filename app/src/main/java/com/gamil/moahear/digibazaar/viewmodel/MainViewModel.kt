package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val productRepository: IProductRepository,isInternetConnected:Boolean): ViewModel() {
    private val _isShownProgressBar =MutableStateFlow(false)
    val isShownProgressBar :MutableStateFlow<Boolean>
        get() = _isShownProgressBar

    init {
        refreshAllDataFromInternet(isInternetConnected)
    }

    private val products=MutableStateFlow<List<ProductsResponse.Product>>(emptyList())


    private val ads=MutableStateFlow<List<SliderPicsResponse.Ad>>(emptyList())
    private fun refreshAllDataFromInternet(hasInternet:Boolean){
        viewModelScope.launch {
            if (hasInternet) _isShownProgressBar.value=true
            
            val newProducts=async {
                productRepository.getAllProducts(hasInternet)
            }
            val newAds=async {
                productRepository.getAllAds(hasInternet)
            }
            updateData(newProducts.await(),newAds.await())
            _isShownProgressBar.value=false
        }
    }
    private fun updateData(products:List<ProductsResponse.Product>, ads:List<SliderPicsResponse.Ad>){
        this.products.value=products
        this.ads.value=ads
    }
}