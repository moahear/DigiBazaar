package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.model.Comment
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.repository.cart.ICartRepository
import com.gamil.moahear.digibazaar.data.repository.comment.ICommentRepository
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import com.gamil.moahear.digibazaar.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: IProductRepository,
    private val commentRepository: ICommentRepository,
    private val cartRepository: ICartRepository
) : ViewModel() {
    private val _product = MutableStateFlow(Constants.PRODUCT_EMPTY)
    val product: StateFlow<ProductsResponse.Product>
        get() = _product.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>>
        get() = _comments.asStateFlow()


    private val _isShowAddingToCartAnimation = MutableStateFlow<Boolean>(false)
    val isShowAddingToCartAnimation: StateFlow<Boolean>
        get() = _isShowAddingToCartAnimation.asStateFlow()

    private val _badgeNumber=MutableStateFlow(0)
    val badgeNumber:StateFlow<Int>
        get() = _badgeNumber.asStateFlow()

    fun getProduct(productId: String, hasInternet: Boolean) {
        getProductFromCache(productId)
        if (hasInternet) {
            getComments(productId)
        }
        getBadgeNumber()
    }

    private fun getProductFromCache(productId: String) {
        viewModelScope.launch() {
            _product.value = productRepository.getProductById(productId)
        }
    }

    private fun getComments(productId: String) {
        viewModelScope.launch {
            _comments.value = commentRepository.getComments(productId)
        }
    }

    fun addComment(
        productId: String,
        message: String,
        isSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            commentRepository.addComment(productId, message, isSuccess)
            _comments.value = commentRepository.getComments(productId)
        }
    }

    fun addToCart(productId: String, addingToCartResult: (String) -> Unit) {
        viewModelScope.launch {
            _isShowAddingToCartAnimation.value = true
            val result = cartRepository.addToCart(productId)
            delay(600)
            _isShowAddingToCartAnimation.value = false
            if (result) addingToCartResult("Product added to cart")
            else addingToCartResult("Product was not added!")

        }
    }

    private fun getBadgeNumber() {
        viewModelScope.launch {
            _badgeNumber.value=cartRepository.getCountInCart()
        }
    }
}