package com.shojishunsuke.kibunnsns.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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

        val viewModel = this.run {
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
            adapter = EmojiRecyclerViewAdapter(requireContext(), viewModel.requestWholeEmoji()) { emojiCode ->
                selectedEmojiCode = emojiCode
            }
            layoutManager = GridLayoutManager(requireContext(), 7)
        }
        parentView.toggleButton.setOnClickListener {
            val isExpanded = parentView.expandableBox.isViewExpanded
            viewModel.toggleArrow(it, isExpanded)
            parentView.expandableBox.toggle()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(parentView)
            .create()

        viewModel.currentPosted.observe(this, Observer { post ->
            DetailPostsFragment.setupFragment(post, requireFragmentManager())
            dismiss()
        })


        parentView.detailDateTextView.text = viewModel.detailDate
        parentView.timeTextView.text = viewModel.timeString

        parentView.setActivityButton.setOnClickListener {
            val emojiDialog = AlertDialog.Builder(requireContext(),R.style.AppTheme_DialogTheme)
                .create()
            val emojiParentView = emojiDialog.layoutInflater.inflate(R.layout.dialog_pop, null)
            emojiParentView.emojiRecyclerView.apply {
                adapter = EmojiRecyclerViewAdapter(requireContext(), viewModel.requestWholeEmoji()) { emojiCode ->
                    selectedEmojiCode = emojiCode
                    parentView.selectedEmojiTextView.text = selectedEmojiCode
                    parentView.selectEmojiBox.visibility = View.VISIBLE
                    parentView.setActivityButton.visibility = View.GONE
                    emojiDialog.dismiss()
                }
                layoutManager = GridLayoutManager(requireContext(), 7)
            }

            emojiDialog.setView(emojiParentView)
            emojiDialog.show()
        }


        parentView.selectEmojiBox.setOnClickListener {
            val emojiDialog = AlertDialog.Builder(requireContext(),R.style.AppTheme_DialogTheme)
                .create()
            val emojiParentView = emojiDialog.layoutInflater.inflate(R.layout.dialog_pop, null)
            emojiParentView.emojiRecyclerView.apply {
                adapter = EmojiRecyclerViewAdapter(requireContext(), viewModel.requestWholeEmoji()) { emojiCode ->
                    selectedEmojiCode = emojiCode
                    parentView.selectedEmojiTextView.text = selectedEmojiCode
                    emojiDialog.dismiss()
                }
                layoutManager = GridLayoutManager(requireContext(), 7)
            }

            emojiDialog.setView(emojiParentView)
            emojiDialog.show()
        }

        parentView.postButton.setOnClickListener {
            val contentText = parentView.contentEditText.text.toString()
            if (contentText.isNotBlank()&& !posted) {
                viewModel.requestPost(contentText, selectedEmojiCode)
                posted = true
            } else {
                Toast.makeText(requireContext(), "メッセージを入力してください", Toast.LENGTH_SHORT).show()
            }
        }
        parentView.cancelButton.setOnClickListener {
           dismiss()
        }



        viewModel.currentEmojiList.observe(this, Observer {
            currentEmojiListAdapter.setValue(it)
        })

        return dialog
    }


}