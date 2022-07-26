package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {

    val data: LiveData<List<Post>>

    fun likeById(postId : Long)
    fun shareById(postId : Long)
    fun save(post: Post)
    fun removeById(postId : Long)

    companion object{
        const val NEW_POST_ID = 0L
    }
}