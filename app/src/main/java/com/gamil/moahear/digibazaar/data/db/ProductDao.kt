package com.gamil.moahear.digibazaar.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gamil.moahear.digibazaar.data.model.ProductsResponse

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(products: List<ProductsResponse.Product>)

    @Query("SELECT * FROM PRODUCT_TABLE")
    fun getAll(): List<ProductsResponse.Product>

    @Query("SELECT * FROM PRODUCT_TABLE WHERE productId =:productId ")
    fun getProductById(productId: String): ProductsResponse.Product

    @Query("SELECT * FROM PRODUCT_TABLE WHERE category =:productName ")
    fun getProductsByCategory(productName: String): List<ProductsResponse.Product>
}