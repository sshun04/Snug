package com.shojishunsuke.kibunnsns.presentation.secen.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.presentation.secen.tutorial.adapter.TutorialPagerAdapter
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TutorialActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val pagerAdapter = TutorialPagerAdapter(supportFragmentManager)
        tutorialViewPager.adapter = pagerAdapter
        tutorialTabLayout.setupWithViewPager(tutorialViewPager)

        nextButton.setOnClickListener {
            tutorialViewPager.setCurrentItem(tutorialViewPager.currentItem +1,true)
        }

        skipButton.setOnClickListener {
            finishTutorial()
        }

    }

    private fun finishTutorial() {
        finish()
    }
}
