package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostsFragmentsViewModel:ViewModel() {
    private val useCase = HomePostsFragmentUseCase()
    lateinit var selectedPost:Post
    val relatedPosts = MutableLiveData<List<Post>>()

    fun getAppropriateIcon(sentiScore: Float):String = useCase.getSmilyes(sentiScore)

    fun getIconRef(uriString: String):StorageReference = useCase.getIconStorageRef(uriString)

    fun onPostSelected(view: View, post: Post,isHome:Boolean){
        selectedPost = post
        if (isHome){
            Navigation.findNavController(view).navigate(R.id.action_move_to_detail_from_home)
        }else {

        }
        GlobalScope.launch {
            val posts = useCase.loadRelatedPosts(post)
            launch (Dispatchers.IO){
                relatedPosts.postValue(posts)
            }
        }
    }

}