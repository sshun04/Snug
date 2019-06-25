package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.EmojiRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDialogViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory
import com.shojishunsuke.kibunnsns.customview.ExpandableLayout

class PostDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parentView = activity?.layoutInflater?.inflate(R.layout.fragment_dialog_post, null)


        val dialogViewModel = ViewModelProviders.of(this).get(PostDialogViewModel::class.java)

        val sharedViewModel = activity?.run {
            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val postButton = parentView?.findViewById<MaterialButton>(R.id.postButton)
        val expandableLayout = parentView?.findViewById<ExpandableLayout>(R.id.expandableBox)
        val editText = parentView?.findViewById<EditText>(R.id.contentEditText)
        val toggleButton = parentView?.findViewById<ImageView>(R.id.toggleButton)
        val currentEmojiRecyclerView = parentView?.findViewById<RecyclerView>(R.id.currentEmojiList).apply {
            this?.adapter = EmojiRecyclerViewAdapter(requireContext(),sharedViewModel, dialogViewModel.currentEmoji)
            this?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        val wholeEmojiRecyclerView = parentView?.findViewById<RecyclerView>(R.id.emojiRecyclerView).apply {
            this?.adapter = EmojiRecyclerViewAdapter(requireContext(),sharedViewModel, dialogViewModel.wholeEmoji)
            this?.layoutManager = GridLayoutManager(requireContext(), 5)
        }


        toggleButton?.setOnClickListener {
            val isExpanded = expandableLayout?.isViewExpanded ?: false

            dialogViewModel.toggleArrow(it,isExpanded)
            expandableLayout?.toggle()
        }

        postButton?.setOnClickListener {
            val contentText = editText?.text.toString()
            if (contentText.isBlank()) return@setOnClickListener
            sharedViewModel.onPost(contentText)


        }


        val dialog = AlertDialog.Builder(requireContext())
            .setView(parentView)
            .create()

        return dialog
    }
}