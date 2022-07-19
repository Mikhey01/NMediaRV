package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.imtl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()
    val data get() = repository.data

    fun onLikeClicked(id : Long) = repository.likes(postId = id)
    fun onShareClicked(post: Post) = repository.share()
}