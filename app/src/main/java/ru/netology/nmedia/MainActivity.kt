//package ru.netology.nmedia
//
////import ru.netology.nmedia.adapter.PostAdapter
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import ru.netology.nmedia.activity.NewPostResultContract
//import ru.netology.nmedia.adapter.PostAdaptor
//import ru.netology.nmedia.databinding.ActivityMainBinding
//import ru.netology.nmedia.viewModel.PostViewModel
//
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val viewModel: PostViewModel by viewModels()
//        val adapter = PostAdaptor(viewModel)
//
//        binding.list.adapter = adapter
//
//        viewModel.data.observe(this) { posts ->
//            adapter.submitList(posts)
//        }
//
//        viewModel.shareEvent.observe(this) { post ->
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, post.content)
//                type = "text/plain"
//            }
//            val shareIntent =
//                Intent.createChooser(intent, "Поделиться")
//            startActivity(shareIntent)
//        }
//
//        viewModel.playVideo.observe(this) { videoUrl ->
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(intent)
//            }
//        }
//
//
//        val newPostLauncher = registerForActivityResult(NewPostResultContract) { postContent ->
//            postContent ?: return@registerForActivityResult
//            viewModel.onSaveButtonClicked(postContent)
//        }
//
//        binding.fab.setOnClickListener {
//            viewModel.onAddClicked()
//        }
//
//        viewModel.navigateToPostContentEvent.observe(this) { postContent ->
//                newPostLauncher.launch(postContent)
//
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
