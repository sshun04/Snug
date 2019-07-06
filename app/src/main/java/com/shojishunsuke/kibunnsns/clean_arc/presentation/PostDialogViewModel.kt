package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.SharedPrefRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostDialogUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostDialogViewModel(context: Context) : ViewModel() {

    private val postUseCase: PostDialogUseCase
    private val _currentPosted = MutableLiveData<Post>()

    val currentPosted:LiveData<Post> get() = _currentPosted


    init {
        val analysisRepository = NaturalLanguageAnalysisRepository(context)
        val dataConfigRepository = SharedPrefRepository(context)
        postUseCase = PostDialogUseCase(dataConfigRepository,analysisRepository)
    }


    fun toggleArrow(view: View, isExpanded: Boolean = false) {
        if (isExpanded) {
            startRotate(view, -180f, 0f)
        } else {
            startRotate(view, 0f, -180f)
        }
    }

    private fun startRotate(view: View, startRotate: Float, endRotate: Float) {
        val rotateAnimation =
            RotateAnimation(startRotate, endRotate, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        view.startAnimation(rotateAnimation)
    }

    fun requestPost(content: String, emojiCode: String){
        GlobalScope.launch {
            val post = postUseCase.generatePost(content, emojiCode)
            launch(Dispatchers.IO) {
                _currentPosted.postValue(post)
            }

        }
    }
    fun requestWholeEmoji() = postUseCase.loadWholeEmoji()
    fun requestCurrentEmoji() = postUseCase.loadCurrentEmoji()

    fun addCurrentEmoji(emojiCode:String){
        postUseCase.updateCurrentEmoji(emojiCode)
    }
}