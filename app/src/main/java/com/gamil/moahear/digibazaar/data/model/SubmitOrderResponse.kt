package com.gamil.moahear.digibazaar.data.model

data class SubmitOrderResponse(
    val success: Boolean,
    val orderId: Int,
    val paymentLink: String
)
