package com.shojishunsuke.kibunnsns.presentation.secen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.presentation.secen.main.MainActivity
import com.shojishunsuke.kibunnsns.presentation.secen.main.tutorial.TutorialActivity
import com.shojishunsuke.kibunnsns.presentation.secen.setting.SplashViewModel
import com.shojishunsuke.kibunnsns.presentation.secen.setting.SplashViewModelFactory

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = this.run {
            ViewModelProviders.of(this, SplashViewModelFactory(application))
                .get(SplashViewModel::class.java)
        }

        if (viewModel.isInitialized()){
            MainActivity.start(this)
        }else{
            TutorialActivity.start(this)
        }
    }
}
