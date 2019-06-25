package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue

import com.shojishunsuke.kibunnsns.clean_arc.domain.PostDialogUseCase
import com.shojishunsuke.kibunnsns.clean_arc.utils.bd
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

class PostDialogViewModel:ViewModel() {

   private val postUseCase = PostDialogUseCase()
   val wholeEmoji:List<String> = postUseCase.loadWholeEmoji()
   val currentEmoji:List<String> = postUseCase.loadCurrentEmoji()

   fun toggleArrow(view:View,isExpanded:Boolean = false){
    if (isExpanded){
        startRotate(view,-180f,0f)
    }else{
        startRotate(view,0f,-180f)
    }


   }
    private fun startRotate(view: View, startRotate: Float, endRotate: Float) {
        val rotateAnimation =
            RotateAnimation(startRotate, endRotate, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        view.startAnimation(rotateAnimation)

    }

}