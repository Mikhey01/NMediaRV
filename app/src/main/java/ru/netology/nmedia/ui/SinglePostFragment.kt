package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import resFormat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.SinglePostViewBinding
import ru.netology.nmedia.Post
import ru.netology.nmedia.viewModel.PostViewModel


class SinglePostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()
    private val args by navArgs<SinglePostFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToPostContentEvent.observe(this) { initialContent ->
            val direction =
                SinglePostFragmentDirections.singlePostFragmentToPostContentFragment(
                    initialContent
                ) // навигация
            findNavController().navigate(direction)
        }

        viewModel.playVideo.observe(this) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }

        viewModel.shareEvent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, "Поделиться")
            startActivity(shareIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SinglePostViewBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            val postId = args.postId
            viewModel.addSinglePost(postId)

            viewModel.data.observe(viewLifecycleOwner) { posts ->
                posts.firstOrNull { post ->
                    post.id == postId
                }?.let { bind(it, binding) }
            }

            val popupMenu by lazy {
                PopupMenu(context, binding.singlePostView.menu).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                viewModel.onRemoveClickedSinglePost()
                                val direction =
                                    SinglePostFragmentDirections.singlePostFragmentToFeedFragment()
                                findNavController().navigate(direction)
                                true
                            }
                            R.id.edit -> {
                                viewModel.onEditClickedSinglePost()
                                true
                            }
                            else -> false
                        }
                    }
                }
            }

            with(binding.singlePostView) {
                like.setOnClickListener {
                    viewModel.onLikeClickedSinglePost()
                }
                share.setOnClickListener {
                    viewModel.onShareClickedSinglePost()
                }
                videoBanner.setOnClickListener {
                    viewModel.onPlayClickedSinglePost()
                }
                playVideoButton.setOnClickListener {
                    viewModel.onPlayClickedSinglePost()
                }
                menu.setOnClickListener { popupMenu.show() }
            }
        }.root

    private fun bind(post: Post, binding: SinglePostViewBinding) {
        with(binding.singlePostView) {
            share.text = resFormat(post.countShare)
            like.text = resFormat(post.countLikes)

            authorName.text = post.author
            date.text = post.publisher
            textPost.text = post.content
            like.isChecked = post.likeByMe

            videoGroup.isVisible = post.video != null
        }
    }
}