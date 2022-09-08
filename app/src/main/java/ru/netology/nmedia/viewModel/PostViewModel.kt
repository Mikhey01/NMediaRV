package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.imtl.FilePostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), OnInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)

  //  private val repository: PostRepository = InMemoryPostRepository()
    val data by repository :: data

    val shareEvent = SingleLiveEvent<Post>()
    val navigateToPostContentEvent = SingleLiveEvent<String?>()

    private val currentPost = MutableLiveData<Post?>(null)
    val playVideo = SingleLiveEvent<String>()

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

     fun onAddClicked() {
         navigateToPostContentEvent.call()
     }

    override fun likeClickListener(post: Post) = repository.likeById(post.id)
    override fun shareClickListener(post: Post) {
        repository.shareById(post.id)
       shareEvent.value = post
    }

    override fun removeClickListener(post: Post) = repository.removeById(post.id)
    override fun editClickListener(post: Post) {
        currentPost.value = post
        navigateToPostContentEvent.value = post.content
           }


        override fun onCancelButtonClicked() {
          repository.cancel()
        currentPost.value = null
    }

     override fun onPlayVideoClicked(post: Post) {
         val url: String = requireNotNull(post.video) { // проверяем, что есть url
             "Url must not be null"
         }
         playVideo.value = url
     }
}