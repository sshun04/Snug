package com.shojishunsuke.kibunnsns.presentation.secen.tutorial.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shojishunsuke.kibunnsns.R

class TutorialLastFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutorial_last,container,false)
        return view
    }
}