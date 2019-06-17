package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue

import com.shojishunsuke.kibunnsns.clean_arc.domain.PostDialogUseCase
import com.shojishunsuke.kibunnsns.clean_arc.utils.bd
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

class PostDialogViewModel:ViewModel() {

   private val postUseCase = PostDialogUseCase()


}