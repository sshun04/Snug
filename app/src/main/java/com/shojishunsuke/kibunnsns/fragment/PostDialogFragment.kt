package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.EmojiRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDialogViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.PostDialogViewModelFactory
import kotlinx.android.synthetic.main.fragment_dialog_post.view.*

class PostDialogFragment : DialogFragment() {

    private var selectedEmojiCode: String = ""
    private var posted = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val parentView = requireActivity().layoutInflater.inflate(R.layout.fragment_dialog_post, null)

        val postViewModel = this.run {
            ViewModelProviders.of(this, PostDialogViewModelFactory(requireContext()))
                .get(PostDialogViewModel::class.java)
        }

        val currentEmojiListAdapter = EmojiRecyclerViewAdapter(requireContext()) { emojiCode ->
            selectedEmojiCode = emojiCode
        }
        parentView.currentEmojiList.apply {
            adapter = currentEmojiListAdapter

            this.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        parentView.emojiRecyclerView.apply {
            adapter = EmojiRecyclerViewAdapter(requireContext(), postViewModel.requestWholeEmoji()) { emojiCode ->
                selectedEmojiCode = emojiCode
            }
            layoutManager = GridLayoutManager(requireContext(), 7)
        }
        parentView.toggleButton.setOnClickListener {
            val isExpanded = parentView.expandableBox.isViewExpanded
            postViewModel.toggleArrow(it, isExpanded)
            parentView.expandableBox.toggle()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(parentView)
            .create()

        postViewModel.currentPosted.observe(this, Observer {
            DetailPostsFragment.setupFragment(it, requireFragmentManager())
            dismiss()
        })

        parentView.postButton.setOnClickListener {
            val contentText = parentView.contentEditText.text.toString()
            if (contentText.isNotBlank()) {
                postViewModel.requestPost(contentText, selectedEmojiCode)
            } else {
                Toast.makeText(requireContext(),"メッセージを入力してください",Toast.LENGTH_SHORT).show()
            }
        }

        postViewModel.currentEmojiList.observe(this, Observer {
            currentEmojiListAdapter.setValue(it)
            posted = true
        })
        return dialog
    }
}