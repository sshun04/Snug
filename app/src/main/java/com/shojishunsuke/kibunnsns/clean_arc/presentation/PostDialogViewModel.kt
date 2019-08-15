package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomEmojiRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostDialogUseCase
import com.shojishunsuke.kibunnsns.model.Post
import com.shojishunsuke.kibunnsns.utils.detailDateString
import com.shojishunsuke.kibunnsns.utils.timeOfDayString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PostDialogViewModel(context: Context) : ViewModel() {

    private val postUseCase: PostDialogUseCase
    private val _currentPosted = MutableLiveData<Post>()
    private val date = Calendar.getInstance()


    val timeString = date.timeOfDayString()
    val detailDate = date.detailDateString()
    val currentPosted: LiveData<Post> get() = _currentPosted
    val currentEmojiList: MutableLiveData<List<String>> = MutableLiveData()


    init {
        val analysisRepository = NaturalLanguageAnalysisRepository(context)
        val roomRepository = RoomEmojiRepository()
        postUseCase = PostDialogUseCase(roomRepository, analysisRepository)

        requestCurrentEmojiList()
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

    fun requestPost(content: String, emojiCode: String) {
        GlobalScope.launch {
            val post = postUseCase.generatePost(content, emojiCode,date.time)
            launch(Dispatchers.IO) {
                _currentPosted.postValue(post)

            }

        }
    }



    fun requestWholeEmoji(): MutableList<String> = postUseCase.loadWholeEmoji() as MutableList<String>

    private fun requestCurrentEmojiList() {
        GlobalScope.launch {
            val emojiList = postUseCase.loadCurrentEmoji()
            launch(Dispatchers.IO) {
                currentEmojiList.postValue(emojiList)
            }
        }

    }

    override fun onCleared() {

    }
}