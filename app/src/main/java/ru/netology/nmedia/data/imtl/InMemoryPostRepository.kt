package ru.netology.nmedia.data.imtl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val inishinaPost = listOf(
            Post(
                id = 1,
                author = "Нетология. Университет - профессий",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов" +
                        " по онлайн-маркетингую Затем появились курсы по дизайну, разработке, " +
                        "аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до" +
                        "уверенных профессионалов.но самое важное остается с нами: мы верим, что в каждом" +
                        "уже есть сила, которая заставляет хотетьбольше, целиться выше, бежать быстрее." +
                        "Наша миссия - помочь встать на путь роста и начать цепочку перемен." +
                        " - http://netolo.gy/fyb",
                publisher = "18 мая в 23:47",
                likeByMe = false,
                countLikes = 999,
                countShare = 0,
                countViews = 0,
            ),
            Post(
                id = 3,
                author = "Нетология. Университет - профессий",
                content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \\uD83D\\uDE34\\n",
                publisher = "19 мая в 03:47",
                likeByMe = false,
                countLikes = 1999,
                countShare = 0,
                countViews = 0,
            ),
            Post(
                id = 2,
                author = "Нетология. Университет - профессий",
                content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
                publisher = "20 сентября в 10:14",
                likeByMe = false,
                countLikes = 9,
                countShare = 0,
                countViews = 0,
            ),
            Post(
                id = 8,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
                "18 мая в 23:47",
                likeByMe = false,
                countLikes = 999,
                countShare = 0,
                countViews = 0,
            ),
        )
        data = MutableLiveData(inishinaPost)

    }


    override fun likes(postId: Long) {
        posts = posts.map { post ->
            if (post.id != postId) post else post.copy(
                likeByMe = !post.likeByMe,
                countLikes = if (post.likeByMe) post.countLikes++ else post.countLikes--
            )
        }
        data.value = posts

    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (post.id != postId) post else post.copy(
                countShare = post.countShare++
            )
        }
        data.value = posts
    }
}







