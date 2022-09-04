package ru.netology.nmedia.adapter

import ru.netology.nmedia.Post

interface OnInteractionListener {
    fun likeClickListener(post: Post) {}
    fun shareClickListener(post: Post) {}
    fun removeClickListener(post: Post) {}
    fun editClickListener(post: Post) {}
    fun onCancelButtonClicked()
    fun onPlayVideoClicked(post: Post)

}