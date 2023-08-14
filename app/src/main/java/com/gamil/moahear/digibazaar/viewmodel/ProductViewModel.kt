package com.gamil.moahear.digibazaar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamil.moahear.digibazaar.data.model.Comment
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.repository.comment.ICommentRepository
import com.gamil.moahear.digibazaar.data.repository.product.IProductRepository
import com.gamil.moahear.digibazaar.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: IProductRepository,
    private val commentRepository: ICommentRepository
) : ViewModel() {
    private val _product = MutableStateFlow(Constants.PRODUCT_EMPTY)
    val product: StateFlow<ProductsResponse.Product>
        get() = _product.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>>
        get() = _comments.asStateFlow()

    fun getProduct(productId: String, hasInternet: Boolean) {
        getProductFromCache(productId)
        if (hasInternet) {
            getComments(productId)
        }
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
            commentRepository.addComment(productId,message,isSuccess)
            _comments.value = commentRepository.getComments(productId)
        }
    }
}