package com.gamil.moahear.digibazaar.data.model


import com.google.gson.annotations.SerializedName

data class CommentsResponse(
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("success")
    val success: Boolean? // true
)

data class Comment(
    @SerializedName("commentId")
    val commentId: String, // 94
    @SerializedName("text")
    val text: String, // khoob bood
    @SerializedName("userEmail")
    val userEmail: String // hhhh@gmail.com
)
data class AddCommentResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean

)