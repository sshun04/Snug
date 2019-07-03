package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import kotlinx.coroutines.runBlocking

class HomePostsFragmentViewModel:ViewModel() {
    private val useCase = HomePostsFragmentUseCase()

    fun getAppropriateIcon(sentiScore: Float):String = useCase.getSmilyes(sentiScore)

    fun getIconRef(uriString: String):StorageReference = useCase.getIconStorageRef(uriString)

}