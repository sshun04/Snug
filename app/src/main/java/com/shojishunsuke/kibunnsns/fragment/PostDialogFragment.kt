package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
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
import java.text.SimpleDateFormat
import java.util.*

class PostDialogFragment : DialogFragment() {

   private var selectedEmojiCode: String = ""
   private var  posted = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val parentView = requireActivity().layoutInflater.inflate(R.layout.fragment_dialog_post, null)

        val postViewModel = this.run {
            ViewModelProviders.of(this, PostDialogViewModelFactory(requireContext()))
                .get(PostDialogViewModel::class.java)
        }
        val toolbar = parentView.findViewById<Toolbar>(R.id.dialogToolbar)

        toolbar.setNavigationOnClickListener {
            if (posted)return@setNavigationOnClickListener
            val contentText = parentView.contentEditText.text.toString()
            if (contentText.isBlank()) {
                return@setNavigationOnClickListener
            } else {
                postViewModel.requestPost(contentText, selectedEmojiCode)
                posted = true
            }
        }

        parentView.currentEmojiList.apply {
            adapter =
                EmojiRecyclerViewAdapter(requireContext(), postViewModel.requestCurrentEmoji()) { emojiCode ->
                    postViewModel.addCurrentEmoji(emojiCode)
                    selectedEmojiCode = emojiCode
                }
            this.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        parentView.emojiRecyclerView.apply {
            adapter = EmojiRecyclerViewAdapter(requireContext(), postViewModel.requestWholeEmoji()) { emojiCode ->
                postViewModel.addCurrentEmoji(emojiCode)
                selectedEmojiCode = emojiCode
            }
            layoutManager = GridLayoutManager(requireContext(), 5)
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

        return dialog
    }
}