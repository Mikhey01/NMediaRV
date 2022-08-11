package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.imtl.InMemoryPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), OnInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()
    val data by repository :: data

    val shareEvent = SingleLiveEvent<Post>()
    val navigateToPostContentEvent = SingleLiveEvent<Unit>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            publisher = "Today",
            countLikes = 0,
            countShare = 0,
            countViews = 0,
            likeByMe = false
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun likeClickListener(post: Post) = repository.likeById(post.id)
    override fun shareClickListener(post: Post) {
        repository.shareById(post.id)
       shareEvent.value = post
    }

    override fun removeClickListener(post: Post) = repository.removeById(post.id)
    override fun editClickListener(post: Post) {
        currentPost.value = post
           }

    override fun cancelClickListener() {
      repository.cancel()
        currentPost.value = null
    }

    override fun onCancelButtonClicked() {
          repository.cancel()
        currentPost.value = null
    }

    fun save() {
        navigateToPostContentEvent.call()
    }


}