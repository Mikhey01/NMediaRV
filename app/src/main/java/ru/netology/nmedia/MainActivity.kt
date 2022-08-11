package ru.netology.nmedia

//import ru.netology.nmedia.adapter.PostAdapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.card_post.*
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.nmedia.activity.NewPostActivity
import ru.netology.nmedia.activity.NewPostResultContract
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
        }

        binding.cancelButton.setOnClickListener {
            viewModel.onCancelButtonClicked()
        }

        viewModel.shareEvent.observe(this) { post ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, "Поделиться")
            startActivity(shareIntent)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.content) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    binding.group.visibility = View.VISIBLE
                    requestFocus()
                } else {
                    clearFocus()
                    hideKeyboard()
                    binding.group.visibility = View.INVISIBLE

                }
            }
        }
        val newPostLauncher = registerForActivityResult(NewPostResultContract) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)

        }
        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}














