package ru.netology.nmedia.viewModel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.imtl.FilePostRepository
import ru.netology.nmedia.ui.NewPostFragment.Companion.longArg
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), OnInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)

  //  private val repository: PostRepository = InMemoryPostRepository()
    val data by repository :: data

    val shareEvent = SingleLiveEvent<String>()
    val navigateToPostContentEvent = SingleLiveEvent<String?>()
    val navigateToSinglePostScreenEvent = SingleLiveEvent<Long>()

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
       shareEvent.value = post.content
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

    override fun onPostClicked(postId: Long) {
        navigateToSinglePostScreenEvent.value = postId
    }

    fun deleteById(id: Long) = repository.removeById(id)


//    fun addSinglePost(postId: Long) {
//        newCurrentPost.value = data.value?.firstOrNull { post ->
//            post.id == postId
//        }
//    }
//
//    fun onLikeClickedSinglePost() {
//        newCurrentPost.value?.let { repository.likeById(it.id) }
//    }
//
//    fun onShareClickedSinglePost() {
//        newCurrentPost.value?.let { repository.shareById(it.id) }
//        shareEvent.value = currentPost.value?.content
//    }
//
//    fun onRemoveClickedSinglePost() {
//        newCurrentPost.value?.let { repository.removeById(it.id) }
//    }
//
//    fun onEditClickedSinglePost() {
//        navigateToPostContentEvent.value = newCurrentPost.value?.content
//    }
//
//    fun onPlayClickedSinglePost() {
//        val url: String = requireNotNull(newCurrentPost.value?.video) { // проверяем, что есть url
//            "Url must not be null"
//        }
//        playVideo.value = url
//    }

}