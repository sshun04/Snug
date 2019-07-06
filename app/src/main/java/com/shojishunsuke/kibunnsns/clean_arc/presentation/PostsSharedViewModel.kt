package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.SharedPrefRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.MainActivityUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

//class PostsSharedViewModel(context: Context) : ViewModel() {
//
//    private val _postsList = MutableLiveData<List<Post>>()
//    private val useCase: MainActivityUsecase
//
//
//
//    val postsList: LiveData<List<Post>> get() = _postsList
//
//    init {
//
//        val languageRepository = NaturalLanguageAnalysisRepository(context)
//        val dataConfigRepository = SharedPrefRepository(context)
//
//        useCase = MainActivityUsecase(languageRepository,dataConfigRepository)
//
//    }
//
//
//    fun getFormattedDate(date: Date): String = useCase.formatDate(date)
//}