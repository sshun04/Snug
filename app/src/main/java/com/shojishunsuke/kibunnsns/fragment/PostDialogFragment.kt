package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDialogViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory
import com.shojishunsuke.kibunnsns.customview.ExpandableLayout

class PostDialogFragment : DialogFragment() {


    lateinit var postViewModel: PostDialogViewModel
    lateinit var sharedViewModel: PostsSharedViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parentView = activity?.layoutInflater?.inflate(R.layout.fragment_dialog_post, null)

        sharedViewModel = activity?.run {
            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val postButton = parentView?.findViewById<MaterialButton>(R.id.postButton)
        val expandableLayout = parentView?.findViewById<ExpandableLayout>(R.id.expandableBox)

        expandableLayout?.setOnClickListener {expandableLayout.collapse()}
        postButton?.setOnClickListener {
            dismiss()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(parentView)
            .create()




        return dialog
    }

//        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//
//
//            val dialog= Dialog(context)
//
//        dialog.window?.let { it.requestFeature(Window.FEATURE_NO_TITLE) }
//
//
//        try {
//            dialog.setContentView(R.layout.fragment_dialog_post)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//
//
//        postViewModel = ViewModelProviders.of(this).get(PostDialogViewModel::class.java)
//        sharedViewModel = activity?.run {
//            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
//        } ?: throw Exception("Invalid Activity")
//
//
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val toolbar = dialog.findViewById<Toolbar>(R.id.toolbar).apply {
//            title = "KibunnSns"
//            setNavigationIcon(R.drawable.ic_close_black_24dp)
//            setNavigationOnClickListener {
//                dismiss()
//            }
//        }
//        val contentEditText = dialog.findViewById<EditText>(R.id.contentEditText)
//        val postButton = dialog.findViewById<Button>(R.id.postButton)
//
//
//        postButton.setOnClickListener {
//            val content = contentEditText.text.toString()
//            sharedViewModel.onPost(content)
//            dialog.dismiss()
//        }
//
//
////        var realsize = Point()
////        display.getRealSize(realsize)
////        val height = (realsize.y * 0.5).toInt()
////        val width = (realsize.x * 0.9).toInt()
////
////        dialog.window.setLayout(height, width)
//
//
//        return dialog
//
//    }

    override fun onStart() {
        super.onStart()


    }
}