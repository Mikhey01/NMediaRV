package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

fun likesCounters(count: Long): String {
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

class PostAdaptor(
    private val interectionListener: OnInteractionListener
) : ListAdapter<Post, PostAdaptor.PostViewHolder>(PostDiffItemCallback()) {

    class PostViewHolder(
        private val binding: CardPostBinding,

        private val listener: OnInteractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            authorName.text = post.author
            date.text = post.publisher
            textPost.text = post.content

            share.text = likesCounters(post.countShare)
            like.isChecked = post.likeByMe
            like.text = likesCounters((post.countLikes))
            videoGroup.isVisible = post.video != null

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { Item ->
                        when (Item.itemId) {
                            R.id.remove -> {
                                listener.removeClickListener(post)
                                true
                            }
                            R.id.edit -> {
                                listener.editClickListener(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            like.setOnClickListener {
                listener.likeClickListener(post)
            }
            share.setOnClickListener {
                listener.shareClickListener(post)
            }
            binding.videoBanner.setOnClickListener {
                listener.onPlayVideoClicked(post)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, interectionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}