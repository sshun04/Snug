package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDialogViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory

class PostDialogFragment : DialogFragment() {

    lateinit var postViewModel: PostDialogViewModel
    lateinit var sharedViewModel: PostsSharedViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(context)

        postViewModel = ViewModelProviders.of(this).get(PostDialogViewModel::class.java)
        sharedViewModel = activity?.run {
            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
        }?:throw Exception("Invalid Activity")

        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )

        try {
            dialog.setContentView(R.layout.fragment_dialog_post)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val contentEditText = dialog.findViewById<EditText>(R.id.contentEditText)
        val postButton = dialog.findViewById<MaterialButton>(R.id.postButton)


        postButton.setOnClickListener {

            val content = contentEditText.text.toString()
            sharedViewModel.onPost(content)
        }




        return dialog

    }
}