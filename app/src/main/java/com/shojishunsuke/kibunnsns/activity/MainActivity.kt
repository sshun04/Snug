package com.shojishunsuke.kibunnsns.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.MainActivityViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.MainActivityViewModelFactory
import com.shojishunsuke.kibunnsns.navigation.CustomNavHostFragment
import com.shojishunsuke.kibunnsns.navigation.CustomNavigator

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    lateinit var mainViewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)


        mainViewModel = ViewModelProviders.of(this,MainActivityViewModelFactory(supportFragmentManager)).get(MainActivityViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        bottomNavigation = findViewById(R.id.bottom_navigation)
        val navigator = CustomNavigator(this,navHostFragment.childFragmentManager,R.id.nav_host_fragment)


        navController.navigatorProvider+= navigator

        navController.setGraph(R.navigation.navigation)

        bottomNavigation.setupWithNavController(navController)

        fab.setOnClickListener {
            mainViewModel.setupPostFragment()
        }


    }

    override fun onNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()



}

