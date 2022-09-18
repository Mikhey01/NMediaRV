package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdaptor
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.ui.NewPostFragment.Companion.longArg
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            val adapter = PostAdaptor(viewModel)

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

            viewModel.playVideo.observe(viewLifecycleOwner) { videoUrl ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                }
            }

            setFragmentResultListener(
                requestKey = NewPostFragment.REQUEST_KEY
            ) { requestKey, bundle ->
                if (requestKey != NewPostFragment.REQUEST_KEY) return@setFragmentResultListener
                val newPostContent = bundle.getString(
                    NewPostFragment.RESULT_KEY
                ) ?: return@setFragmentResultListener
                viewModel.onSaveButtonClicked(newPostContent)
            }

            //переход на NewPostFragment
            viewModel.navigateToPostContentEvent.observe(viewLifecycleOwner) { initialContent ->
                val direction =
                    FeedFragmentDirections.toPostContentFragment(initialContent) // навигация
                findNavController().navigate(direction)
            }

            //переход на SinglePost
            viewModel.navigateToSinglePostScreenEvent.observe(viewLifecycleOwner) { postId ->

                val direction = FeedFragmentDirections.toSinglePostFragment(postId)
                findNavController().navigate(direction)
            }
            binding.list.adapter = adapter // чтобы список обновился
            viewModel.data.observe(viewLifecycleOwner) { posts -> // привязываемся к ЖЦ view
                adapter.submitList(posts) // сравнивает старый и новый списки
            }
            binding.fab.setOnClickListener {
                viewModel.onAddClicked()
            }
        }.root
}