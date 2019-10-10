package com.shojishunsuke.kibunnsns.presentation.secen.main.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter.TutorialPagerAdapter
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, View.OnClickListener {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TutorialActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var pagePosition = 0
    private lateinit var viewModel: TutorialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        viewModel = run {
            ViewModelProviders.of(this, TutorialViewModel.TutorialViewModelFactory(application))
                .get(TutorialViewModel::class.java)
        }

        val pagerAdapter = TutorialPagerAdapter(supportFragmentManager)
        tutorialViewPager.apply {
            adapter = pagerAdapter
            addOnPageChangeListener(this@TutorialActivity)
        }
        tutorialTabLayout.setupWithViewPager(tutorialViewPager)
        buttonNext.setOnClickListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        pagePosition = position
        when (pagePosition) {
            TutorialPagerAdapter.TutorialPage.Last.value -> {
                buttonNext.text = resources.getString(R.string.button_get_started)
            }
            else -> {
                buttonNext.text = resources.getString(R.string.button_next)
            }
        }
    }

    override fun onClick(p0: View?) {
        if (pagePosition == TutorialPagerAdapter.TutorialPage.Last.value) {
            finishTutorial()
        } else {
            tutorialViewPager.setCurrentItem(pagePosition + 1, true)
        }
    }

    private fun finishTutorial() {
        viewModel.onFinishTutorial()
        finish()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            return false
        }
        return true
    }

}
