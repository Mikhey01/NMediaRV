package ru.netology.nmedia

//import ru.netology.nmedia.adapter.PostAdapter

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.nmedia.adapter.PostAdaptor
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdaptor(viewModel)


        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyboard()
            }

//            viewModel.currentPost.observe(this) { currentPost ->
//                binding.content.setText(currentPost?.content)
//            }
            binding.cancelButton.setOnClickListener {
                viewModel.onCancelButtonClicked()
            }

        viewModel.currentPost.observe(this) { currentPost ->
            with (binding.content) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                   // binding.content.text =   currentPost.content
                    binding.group.visibility = View.VISIBLE
                    requestFocus()

                } else {
                    clearFocus()
                   // binding.group.visibility = View.GONE
                    hideKeyboard()
                }
            }
        }
        }
    }
}











