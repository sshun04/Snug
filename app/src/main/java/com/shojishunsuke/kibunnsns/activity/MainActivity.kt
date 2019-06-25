package com.shojishunsuke.kibunnsns.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.MainActivityViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory
import com.shojishunsuke.kibunnsns.navigation.CustomNavigator

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    lateinit var mainViewModel: MainActivityViewModel
    private var isInitialized = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        mainViewModel = ViewModelProviders.of(this)
            .get(MainActivityViewModel::class.java)
        val sharedViewModel = this.run {
            ViewModelProviders.of(this, SharedViewModelFactory(this)).get(PostsSharedViewModel::class.java)
        }


        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        bottomNavigation = findViewById(R.id.bottom_navigation)
        val navigator = CustomNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.navigation)
        bottomNavigation.setupWithNavController(navController)

        fab.setOnClickListener {
            mainViewModel.setupPostFragment(supportFragmentManager)
            isInitialized = true
        }


    }

    override fun onNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()


    override fun onResume() {
        super.onResume()
        mainViewModel.setupPostFragment(supportFragmentManager)
    }


}

