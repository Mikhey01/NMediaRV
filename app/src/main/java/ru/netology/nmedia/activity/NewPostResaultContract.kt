//package ru.netology.nmedia.activity
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import androidx.activity.result.contract.ActivityResultContract
//
// object NewPostResultContract : ActivityResultContract<String?, String?>() {
//
//     override fun createIntent(context: Context, input: String?): Intent =
//         Intent(context, NewPostActivity::class.java)
//             .putExtra(Intent.EXTRA_TEXT, input)
//
//     override fun parseResult(resultCode: Int, intent: Intent?): String? {
//         if (resultCode != Activity.RESULT_OK) return null
//         intent ?: return null
//         return intent.getStringExtra(NewPostActivity.POST_CONTENT_EXTRA_KEY)
//
//     }
//}



