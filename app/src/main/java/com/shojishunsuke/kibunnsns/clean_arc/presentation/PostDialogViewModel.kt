package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomEmojiRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostDialogUseCase
import com.shojishunsuke.kibunnsns.model.CloudPost
import com.shojishunsuke.kibunnsns.utils.detailDateString
import com.shojishunsuke.kibunnsns.utils.timeOfDayString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PostDialogViewModel(context: Context) : ViewModel() {

    private val postUseCase: PostDialogUseCase
    private val _currentPosted = MutableLiveData<CloudPost>()
    private val date = Calendar.getInstance()


    val timeString = date.timeOfDayString()
    val detailDate = date.detailDateString()
    val currentPosted: LiveData<CloudPost> get() = _currentPosted
    private val currentEmojiList: MutableLiveData<List<String>> = MutableLiveData()


    init {
        val analysisRepository = NaturalLanguageAnalysisRepository(context)
        val roomRepository = RoomEmojiRepository()
        postUseCase = PostDialogUseCase(roomRepository, analysisRepository)
        requestCurrentEmojiList()
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