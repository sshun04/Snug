package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.RecordRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.RecordFragmentViewModel

class RecordFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_record, container, false)

        val viewModel = activity?.run {
            ViewModelProviders.of(this).get(RecordFragmentViewModel::class.java)
        }?:throw Exception("Invalid Activity")



        val button  = view.findViewById<Button>(R.id.button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recordRecyclerView).apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }


        viewModel.liveData.observe(this, Observer {
            recyclerView.adapter = RecordRecyclerViewAdapter(requireContext(),it)
        })

        button.setOnClickListener { viewModel.loadWholeCollection() }

        return view


    }
}