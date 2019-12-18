package com.shojishunsuke.kibunnsns.presentation.secen.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.data.repository.impl.SharedPrefRepository

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoy = SharedPrefRepository(application)
    fun isInitialized(): Boolean = repositoy.isInitialized()


}
class SplashViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(application) as T
    }
}