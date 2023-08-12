package com.gamil.moahear.digibazaar.data.repository.product

import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse

interface IProductRepository {
    suspend fun getAllProducts(hasInternet:Boolean):List<ProductsResponse.Product>
    suspend fun getAllAds(hasInternet:Boolean):List<SliderPicsResponse.Ad>
    suspend fun getProductsByCategory(categoryName:String):List<ProductsResponse.Product>
    suspend fun getProductById(productId:String):ProductsResponse.Product
}