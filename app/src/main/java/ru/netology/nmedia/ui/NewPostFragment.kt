package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.PostContentFragmentBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment() {

    private val args by navArgs<NewPostFragmentArgs>()
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostContentFragmentBinding.inflate(layoutInflater, container, false)
        .also { binding ->

            binding.edit.setText(args.initialContent)
            binding.edit.requestFocus()

            arguments?.textArg
                ?.let(binding.edit::setText)

            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }

        }.root

    private fun onOkButtonClicked(binding: PostContentFragmentBinding) {
        if (!binding.edit.text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, binding.edit.text.toString())
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
//        val direction =
//            NewPostFragmentDirections.postContentFragmentToFeedFragment() // навигация
        findNavController().navigateUp()

    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postContent"

        var Bundle.textArg: String? by StringArg.StringArg

            var Bundle.longArg: Long by StringArg.LongArg

    }
}