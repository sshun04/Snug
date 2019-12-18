package com.shojishunsuke.kibunnsns.presentation.secen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.presentation.secen.main.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isInitialized: Boolean = false

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var auth: FirebaseAuth

    companion object {
        fun start(context: Context) {
            val intent = createIntent(context)
            context.startActivity(intent)
        }

        private fun createIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        mainViewModel = run {
            ViewModelProviders.of(
                this,
                MainActivityViewModel.MainActivityViewModelFactory(application)
            )
                .get(MainActivityViewModel::class.java)
        }
        if (!mainViewModel.isInitialized()) {
            TutorialActivity.start(this)
        }

        fab.setOnClickListener {
            mainViewModel.setupPostFragment(supportFragmentManager)
            isInitialized = true
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            if (!mainViewModel.isNavigationInitialized) updateUi()
        } else {
            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        mainViewModel.onAuthSuccess()
                        updateUi()
                    }else{
                        Log.d("TAG",task.exception?.message)
                    }
                }
        }
    }

    override fun onNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun updateUi() {
        this.mainActivityProgressbar.visibility = View.GONE
        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        bottomNavigation = findViewById(R.id.bottom_navigation)
        val navigator =
            MainBottomNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.navigation)
        bottomNavigation.setupWithNavController(navController)
        mainViewModel.isNavigationInitialized = true
    }
}