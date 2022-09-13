//package ru.netology.nmedia.activity
//
//import android.app.Activity
//import androidx.activity.viewModels
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.MutableLiveData
//import kotlinx.android.synthetic.main.activity_main.view.*
//import ru.netology.nmedia.Post
//import ru.netology.nmedia.databinding.ActivityNewPostBinding
//import ru.netology.nmedia.util.hideKeyboard
//import ru.netology.nmedia.util.showKeyboard
//import ru.netology.nmedia.viewModel.PostViewModel
//
//class NewPostActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityNewPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val viewModel: PostViewModel by viewModels()
//        val editableText = intent?.extras?.getString(Intent.EXTRA_TEXT) ?: ""
//        binding.edit.setText(editableText)
//
//        binding.edit.requestFocus()
//        binding.ok.setOnClickListener {
//            onOkButtonClicked(binding.edit.text?.toString())
//        }
//    }
//
//    private fun onOkButtonClicked(postContent: String?) {
//        val intent = Intent()
//        if (postContent.isNullOrBlank()) {
//            setResult(Activity.RESULT_CANCELED, intent)
//        } else {
//            intent.putExtra(POST_CONTENT_EXTRA_KEY, postContent)
//            setResult(Activity.RESULT_OK, intent)
//        }
//        finish()
//    }
//
//    companion object {
//        const val POST_CONTENT_EXTRA_KEY = "postContent"
//    }
//}