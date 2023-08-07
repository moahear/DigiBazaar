package com.gamil.moahear.digibazaar.data.repository.product

import com.gamil.moahear.digibazaar.data.db.ProductDao
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse
import com.gamil.moahear.digibazaar.data.source.ApiServices

class ProductRepositoryImpl(private val apiServices: ApiServices,private val productDao: ProductDao):IProductRepository {
    override suspend fun getAllProducts(hasInternet:Boolean): List<ProductsResponse.Product> {
        if (hasInternet){
            //Get data from internet
            val data=apiServices.getAllProducts().body()
            data?.let {
                if (it.success){
                    productDao.insertOrUpdate(data.products)
                    return data.products
                }
            }
        }
        else{
            //Get data from local
            return productDao.getAll()
        }
        return listOf()
    }

    override suspend fun getAllAds(hasInternet:Boolean): List<SliderPicsResponse.Ad> {
          return apiServices.getAllAds().body()?.ads ?: listOf()
    }
}