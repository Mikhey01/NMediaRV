package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val publisher: String,
    var countLikes: Long,
      var countShare: Long,
    var countViews: Long,
    var likeByMe: Boolean = false
)



