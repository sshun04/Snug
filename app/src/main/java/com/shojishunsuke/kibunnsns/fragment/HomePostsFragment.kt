package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.HomePostsFragmentViewModel

class HomePostsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts,container,false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView)
        val homePostsFragmentViewModel = ViewModelProviders.of(this).get(HomePostsFragmentViewModel::class.java)

        recyclerView.adapter = homePostsFragmentViewModel.setUpRecyclerViewAdapter(context!!)
        recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

        return view
    }
}