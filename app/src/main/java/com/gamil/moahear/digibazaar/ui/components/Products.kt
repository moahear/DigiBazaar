package com.gamil.moahear.digibazaar.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse

@Composable
fun Products(tags:List<String>,products:List<ProductsResponse.Product>,ads:List<SliderPicsResponse.Ad>) {
    Column {
        tags.forEachIndexed { index, _ ->
            val filterProductsWithTag=products.filter {product->
                product.tags==tags[index]
            }
            Log.i("8888", "Products:$ads ")
            ProductSubject(subject = tags[index], products = filterProductsWithTag.shuffled())
            if (ads.size>=2)
            if (index==1 ||index==2){
                ImageAdvertising(ads[index-1])
            }

        }
    }
}