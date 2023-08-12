package com.gamil.moahear.digibazaar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.gamil.moahear.digibazaar.data.model.ProductsResponse
import com.gamil.moahear.digibazaar.data.model.SliderPicsResponse

@Composable
fun Products(
    tags: List<String>,
    products: List<ProductsResponse.Product>,
    ads: List<SliderPicsResponse.Ad>,
    onProductClicked: (String) -> Unit
) {
    if (products.isNotEmpty()) {
        Column {
            tags.forEachIndexed { index, _ ->
                val filterProductsWithTag = products.filter { product ->
                    product.tags == tags[index]
                }
                ProductSubject(
                    subject = tags[index],
                    products = filterProductsWithTag.shuffled(),onProductClicked)
                // if (ads.size>=2)
                if (index == 1 || index == 2) {
                    ImageAdvertising(ads[index - 1], onProductClicked)
                }

            }
        }
    }
}