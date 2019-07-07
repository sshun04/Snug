package com.shojishunsuke.kibunnsns.clean_arc.domain

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post

class DetailPostsFragmentUsecase {
   private val fireStoreRepository = FireStoreDatabaseRepository()

    fun getPagingOptionBuilder(lifecycleOwner: LifecycleOwner):FirestorePagingOptions<Post>{
        return fireStoreRepository.loadPagingOptions(lifecycleOwner)
    }


}