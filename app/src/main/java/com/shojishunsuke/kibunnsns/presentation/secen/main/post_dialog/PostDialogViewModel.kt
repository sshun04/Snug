package com.shojishunsuke.kibunnsns.presentation.secen.main.post_dialog

import android.app.Application
import androidx.lifecycle.*
import com.shojishunsuke.kibunnsns.data.repository.impl.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.RoomEmojiRepository
import com.shojishunsuke.kibunnsns.domain.model.Post
import com.shojishunsuke.kibunnsns.domain.use_case.PostDialogUseCase
import com.shojishunsuke.kibunnsns.ext.detailDateString
import com.shojishunsuke.kibunnsns.ext.timeOfDayString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PostDialogViewModel(application: Application) : AndroidViewModel(application) {
    private val postUseCase: PostDialogUseCase

    val currentPosted: LiveData<Post> get() = _currentPosted
    private val _currentPosted = MutableLiveData<Post>()

    private val date = Calendar.getInstance()
    val timeString = date.timeOfDayString()
    val detailDate = date.detailDateString()

    private val currentEmojiList: MutableLiveData<List<String>> = MutableLiveData()

    init {
        val analysisRepository = NaturalLanguageAnalysisRepository(getApplication())
        val roomRepository = RoomEmojiRepository()
        postUseCase = PostDialogUseCase(roomRepository, analysisRepository)
        requestCurrentEmojiList()
    }

    fun requestPost(content: String, emojiCode: String) {
        GlobalScope.launch {
            val post = postUseCase.generatePost(content, emojiCode, date.time)
            launch(Dispatchers.IO) {
                _currentPosted.postValue(post)
            }
        }
    }

    fun requestWholeEmoji(): MutableList<String> =
        postUseCase.loadWholeEmoji() as MutableList<String>

    private fun requestCurrentEmojiList() {
        GlobalScope.launch {
            val emojiList = postUseCase.loadCurrentEmoji()
            launch(Dispatchers.IO) {
                currentEmojiList.postValue(emojiList)
            }
        }
    }

    class PostDialogViewModelFactory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostDialogViewModel(application) as T
        }
    }
}