package ru.netology.nmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

//import ru.netology.nmedia.service

typealias ClickListener = (post : Post) -> Unit

fun likesCounters (count: Long): String {
    val result = when {
        count < 1_000 -> count.toString()
        count in 1_000..1_099 -> "1K"
        count in 1_100..9_999 -> String.format("%.1fK", (count / 100).toDouble() / 10)
        count in 10_000..999_999 -> (count / 1000).toString() + "K"
        count in 1_000_000..1_999_999 -> "1M"
        else -> String.format("%.1fМ", (count / 100_000).toDouble() / 10)
    }
    return result
}

class PostAdapter(
   private val likeClickListener: ClickListener,
   private val shareClickListener: ClickListener
) : ListAdapter <Post, PostAdapter.PostViewHolder>(PostDiffItemCallback()) {
    private var list = emptyList<Post>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val likeClickListener: ClickListener,
        private val shareClickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            authorName.text = post.author
            date.text = post.publisher
            textPost.text = post.content
            quantityFavorit.text = likesCounters(post.countLikes)
            quantityShare.text = likesCounters(post.countShare)
            numberViews.text = likesCounters(post.countViews)

            if (post.likeByMe) {
                likes.setImageResource(R.drawable.outline_favorite_border_24)

            } else {
                likes.setImageResource(R.drawable.ic_favorite_24dp)
            }
            likes.setOnClickListener {
                likeClickListener(post)
            }
            share.setOnClickListener {
                shareClickListener(post)
            }
            likes
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeClickListener, shareClickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    //    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
//        PostViewHolder(
//            binding = CardPostBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            ),
//            likeClickListener = likeClickListener,
//            shareClickListener = shareClickListener
//        )
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        val post = getItem(position)
//        holder.bind(post)
//    }
    override fun getItemCount(): Int = list.size



}


class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}