package com.gamil.moahear.digibazaar.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("error", "Error -> ${throwable.message}")
}
fun separateDigit(text: String): String {
    var reversedText = text.reversed()
    var formattedText = ""
    while (reversedText.length > 3) {
        formattedText += "${reversedText.take(3)},"
        reversedText = reversedText.drop(3)
    }
    formattedText += reversedText
    return "${formattedText.reversed()}  Tomans"
}

fun main() {
    println(separateDigit("123456"))
}
