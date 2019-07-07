package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.DetailPostsFragmentViewModel
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetaiPostslFragment : Fragment() {

    companion object {
        private const val EXRA_POST = "post"

        private val enterTransitionSet = TransitionSet().apply {
            val slideAnim = Slide().apply {
                slideEdge = Gravity.RIGHT
                duration = 200
            }
            addTransition(slideAnim)
        }
       private val exitTransitionSet = TransitionSet().apply {
            val slideAnim = Slide().apply {
                slideEdge = Gravity.RIGHT
                duration = 400
            }
            addTransition(slideAnim)
        }


        fun setupFragment(post: Post, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction().also {
                it.add(R.id.rootFragment, getInstance(post))
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .addToBackStack(null)
                    .commit()
            }
        }

        private fun getInstance(post: Post): DetaiPostslFragment {
            val bundle = Bundle().apply {
                putSerializable(EXRA_POST, post)
            }
            return DetaiPostslFragment().apply {
                arguments = bundle
                enterTransition = enterTransitionSet
                exitTransition = exitTransitionSet
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val post = arguments?.getSerializable(EXRA_POST) as Post

        val viewModel = requireActivity().run {
            ViewModelProviders.of(this).get(DetailPostsFragmentViewModel::class.java)
        }.also { it.requestRelatedPosts(post) }

        view.detailPostsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        view.selectedUserName.text = if (post.userName.isNotBlank()) post.userName else "匿名"

        Glide.with(requireContext())
            .load(post.iconPhotoLink)
            .error(R.drawable.defaultback)
            .into(view.selectedUserIcon)
        view.selectedActIcon.text = if (post.actID.isNotBlank()) post.actID else "\uD83D\uDE42"
        view.selectedDate.text = post.date.toString()
        view.selectedContentText.text = post.contentText


        viewModel.relatedPosts.observe(this, Observer { relatedPosts ->
            view.detailPostsRecyclerView.adapter =
                PostsRecyclerViewAdapter(requireContext(), relatedPosts) { selectedPost ->
                    setupFragment(selectedPost, requireFragmentManager())
                }
        })


        return view
    }


}