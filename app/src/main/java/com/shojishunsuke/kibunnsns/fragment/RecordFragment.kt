package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.customview.ExpandableLayout
import org.w3c.dom.Text

class RecordFragment:Fragment() {
    companion object{
        val newInstance= RecordFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frgment_yourself,container,false)

        val expandableLayout = view.findViewById<ExpandableLayout>(R.id.exp)
        val textView = view.findViewById<TextView>(R.id.recordTextView)

      expandableLayout.setOnClickListener { expandableLayout.collapse() }
        expandableLayout.collapse()
        textView.setOnClickListener { expandableLayout.collapse() }

        return view


    }
}