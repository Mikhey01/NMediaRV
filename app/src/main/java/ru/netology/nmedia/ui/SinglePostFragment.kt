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
import ru.netology.nmedia.ui.NewPostFragment.Companion.textArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel


class SinglePostFragment : Fragment() {

    private val args by navArgs<SinglePostFragmentArgs>()
    private lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SinglePostViewBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            val viewModel: PostViewModel by viewModels(
                ownerProducer = ::requireParentFragment
            )

            val postId = args.postId

            viewModel.data.observe(viewLifecycleOwner) { posts ->
                posts.firstOrNull { post ->
                    post.id == postId
                }?.let {
                    post = it
                    bind(it, binding)
                } ?: findNavController().popBackStack()
            }

            val popupMenu by lazy {
                PopupMenu(context, binding.singlePostView.menu).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                findNavController().navigateUp()
                                viewModel.removeClickListener(post)
                                true
                            }
                            R.id.edit -> {
                                findNavController().navigateUp()
                                viewModel.editClickListener(post)
                                true
                            }
                            else -> false
                        }
                    }
                }
            }

            with(binding.singlePostView) {
                like.setOnClickListener {
                    viewModel.likeClickListener(post)
                }
                share.setOnClickListener {
                    viewModel.shareClickListener(post)
                    viewModel.shareEvent.observe(viewLifecycleOwner) { postContent ->
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
                videoBanner.setOnClickListener {
                    viewModel.onPlayVideoClicked(post)
                }
                playVideoButton.setOnClickListener {
                    viewModel.onPlayVideoClicked(post)
                    viewModel.playVideo.observe(viewLifecycleOwner) { videoUrl ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                        if (intent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivity(intent)
                        }
                    }
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