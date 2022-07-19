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
                countViews = 450,
            ),
            Post(
                id = 3,
                author = "Нетология. Университет - профессий",
                content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \\uD83D\\uDE34\\n",
                publisher = "19 мая в 03:47",
                likeByMe = false,
                countLikes = 1999,
                countShare = 0,
                countViews = 210,
            ),
            Post(
                id = 2,
                author = "Нетология. Университет - профессий",
                content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
                publisher = "20 сентября в 10:14",
                likeByMe = false,
                countLikes = 9,
                countShare = 0,
                countViews = 110,
            ),
            Post(
                id = 8,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
                "18 мая в 23:47",
                likeByMe = false,
                countLikes = 999,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 7,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия ",
                "19 мая в 23:47",
                likeByMe = false,
                countLikes = 99,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 4,
                author = "Нетология. Университет - профессий",
                content = " помочь встать ",
                "15 мая в 23:47",
                likeByMe = false,
                countLikes = 9999,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 55,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия начать цепочку перемен.",
                "25 мая в 23:47",
                likeByMe = false,
                countLikes = 115,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 78,
                author = "Нетология. Университет - профессий",
                content = " помочь встать на путь роста и начать цепочку перемен.",
                "18 июня в 23:47",
                likeByMe = false,
                countLikes = 1478,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 888,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия - помочь встать на путь роста .",
                "18 августа в 23:47",
                likeByMe = false,
                countLikes = 999,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 748,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
                "18 января в 23:47",
                likeByMe = false,
                countLikes = 8549,
                countShare = 0,
                countViews = 10,
            ),
            Post(
                id = 1428,
                author = "Нетология. Университет - профессий",
                content = "Наша миссия - помочь  начать цепочку перемен.",
                "18 марта в 23:47",
                likeByMe = false,
                countLikes = 3629,
                countShare = 0,
                countViews = 10,
            ),
        )
        data = MutableLiveData(inishinaPost)

    }


    override fun likes(id: Long) {
                posts = posts.map { post ->
            if (post.id != id) post else post.copy(
                likeByMe = !post.likeByMe,
                countLikes = if (post.likeByMe) post.countLikes++ else post.countLikes--
            )
        }
        data.value = posts

    }

    override fun share() {
        posts = posts.map { post ->
           post.copy(
                countShare = post.countShare++
            )
        }
        data.value = posts
    }
}







