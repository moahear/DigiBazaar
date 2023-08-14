package com.gamil.moahear.digibazaar.data.repository.comment

import com.gamil.moahear.digibazaar.data.model.AddCommentResponse
import com.gamil.moahear.digibazaar.data.model.Comment
import retrofit2.http.Field

interface ICommentRepository {
    suspend fun getComments(productId:String):List<Comment>
    suspend fun addComment(productId:String,message:String,isSuccess:(String)->Unit)
}