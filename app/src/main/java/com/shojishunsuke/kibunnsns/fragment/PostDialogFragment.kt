package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.activity.PostsDetailActivity
import com.shojishunsuke.kibunnsns.adapter.EmojiRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDialogViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.PostDialogViewModelFactory
import com.shojishunsuke.kibunnsns.customview.ExpandableLayout

class PostDialogFragment : DialogFragment() {

    lateinit var expandableLayout: ExpandableLayout
    lateinit var activityEmojiCode:String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parentView =
            activity?.layoutInflater?.inflate(R.layout.fragment_dialog_post, null) ?: throw IllegalArgumentException()


        val postViewModel = requireActivity().run {
            ViewModelProviders.of(this, PostDialogViewModelFactory(requireContext()))
                .get(PostDialogViewModel::class.java)
        }

        val state = ExpandableLayout.State.COLLAPSED
        expandableLayout = parentView.findViewById<ExpandableLayout>(R.id.expandableBox)
        val listBorder = parentView.findViewById<View>(R.id.listBorder)
        val postButton = parentView.findViewById<MaterialButton>(R.id.postButton)
        val editText = parentView.findViewById<EditText>(R.id.contentEditText)
        val toggleButton = parentView.findViewById<ImageView>(R.id.toggleButton)
        val currentEmojiRecyclerView = parentView.findViewById<RecyclerView>(R.id.currentEmojiList).apply {
            this?.adapter =
                EmojiRecyclerViewAdapter(requireContext(), postViewModel.requestCurrentEmoji()) { emojiCode ->
                    postViewModel.addCurrentEmoji(emojiCode)
                    activityEmojiCode =emojiCode
                }
            this?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        val wholeEmojiRecyclerView = parentView.findViewById<RecyclerView>(R.id.emojiRecyclerView).apply {
            this?.adapter = EmojiRecyclerViewAdapter(requireContext(),postViewModel.requestWholeEmoji()){emojiCode->
                postViewModel.addCurrentEmoji(emojiCode)
                activityEmojiCode = emojiCode
            }
            this?.layoutManager = GridLayoutManager(requireContext(), 5)
        }

        toggleButton?.setOnClickListener {
            val isExpanded = expandableLayout.isViewExpanded
            postViewModel.toggleArrow(it, isExpanded)
            expandableLayout.toggle()
        }

        postButton?.setOnClickListener {
            val contentText = editText.text.toString()
            if (contentText.isBlank()) return@setOnClickListener
            postViewModel.requestPost(contentText,activityEmojiCode)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(parentView)
            .create()


        postViewModel.currentPosted.observe(this, Observer {
            PostsDetailActivity.start(requireContext(),it)
        })

        return dialog
    }




}