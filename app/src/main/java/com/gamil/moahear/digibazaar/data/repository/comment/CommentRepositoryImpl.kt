package com.gamil.moahear.digibazaar.data.repository.comment

import com.gamil.moahear.digibazaar.data.model.Comment
import com.gamil.moahear.digibazaar.data.source.ApiServices

class CommentRepositoryImpl(private val apiServices: ApiServices):ICommentRepository {
    override suspend fun getComments(productId: String): List<Comment> {
       val data= apiServices.getComments(productId).body()
        data?.let {
            if (it.success==true){
                return data.comments
            }
        }
        return emptyList()
    }

    override suspend fun addComment(
        productId: String,
        message: String,
        isSuccess: (String) -> Unit
    ) {
        val data=apiServices.addComment(productId,message).body()
        data?.let {
            if (it.success){
                isSuccess.invoke(data.message)
            }
            else{
                isSuccess.invoke("comment was not send")
            }
        }
    }
}