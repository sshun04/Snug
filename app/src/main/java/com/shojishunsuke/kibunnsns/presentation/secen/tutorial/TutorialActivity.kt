package com.shojishunsuke.kibunnsns.presentation.secen.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.presentation.secen.tutorial.adapter.TutorialPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.tutorial.adapter.TutorialPagerAdapter.TutorialPage
import kotlinx.android.synthetic.main.activity_tutorial.*


class TutorialActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    lateinit var viewModel: TutorialViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TutorialActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        viewModel = run {
            ViewModelProviders.of(
                this,
                TutorialViewModel.TutorialActivityViewModelFactory(application)
            ).get(TutorialViewModel::class.java)
        }

        val pagerAdapter = TutorialPagerAdapter(supportFragmentManager)

        tutorialViewPager.addOnPageChangeListener(this)
        tutorialViewPager.adapter = pagerAdapter
        tutorialTabLayout.setupWithViewPager(tutorialViewPager, true)

        nextButton.setOnClickListener {
            tutorialViewPager.setCurrentItem(tutorialViewPager.currentItem + 1, true)
        }

        skipButton.setOnClickListener {
            finishTutorial()
        }

        doneButton.setOnClickListener {
            finishTutorial()
        }

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        when (position) {
            TutorialPage.Last.value -> {
                nextButton.visibility = View.GONE
                doneButton.visibility = View.VISIBLE
            }
            else -> {
                nextButton.visibility = View.VISIBLE
                doneButton.visibility = View.GONE
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    private fun finishTutorial() {
        viewModel.onFinishTutorial()
        finish()
    }
}
