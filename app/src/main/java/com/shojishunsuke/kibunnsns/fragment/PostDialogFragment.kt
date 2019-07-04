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
import kotlinx.android.synthetic.main.fragment_dialog_post.*

class PostDialogFragment : DialogFragment() {

    lateinit var expandableLayout: ExpandableLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parentView = activity?.layoutInflater?.inflate(R.layout.fragment_dialog_post, null) ?: throw IllegalArgumentException()


        val dialogViewModel = ViewModelProviders.of(this).get(PostDialogViewModel::class.java)

        val sharedViewModel = activity?.run {
            ViewModelProviders.of(this, SharedViewModelFactory(requireContext())).get(PostsSharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val state = ExpandableLayout.State.COLLAPSED
        expandableLayout = parentView.findViewById<ExpandableLayout>(R.id.expandableBox)
        val listBorder = parentView.findViewById<View>(R.id.listBorder)
        val postButton = parentView.findViewById<MaterialButton>(R.id.postButton)
        val editText = parentView.findViewById<EditText>(R.id.contentEditText)
        val toggleButton = parentView.findViewById<ImageView>(R.id.toggleButton)
        val currentEmojiRecyclerView = parentView.findViewById<RecyclerView>(R.id.currentEmojiList).apply {
            this?.adapter = EmojiRecyclerViewAdapter(requireContext(),sharedViewModel, sharedViewModel.loadCurrentEmoji())
            this?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        val wholeEmojiRecyclerView = parentView.findViewById<RecyclerView>(R.id.emojiRecyclerView).apply {
            this?.adapter = EmojiRecyclerViewAdapter(requireContext(),sharedViewModel, dialogViewModel.wholeEmoji)
            this?.layoutManager = GridLayoutManager(requireContext(), 5)
        }

        toggleButton?.setOnClickListener {
            val isExpanded = expandableLayout.isViewExpanded
            dialogViewModel.toggleArrow(it,isExpanded)
            expandableLayout.toggle()
        }

        postButton?.setOnClickListener {
            val contentText = editText.text.toString()
            if (contentText.isBlank()) return@setOnClickListener
            sharedViewModel.onPost(contentText)
            dismiss()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(parentView)
            .create()

        return dialog
    }


    override fun onDestroy() {

        super.onDestroy()
    }


}