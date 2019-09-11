package com.shojishunsuke.kibunnsns.presentation.secen.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.shojishunsuke.kibunnsns.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isInitialized: Boolean = false

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        mainViewModel = run {
            ViewModelProviders.of(
                this,
                MainActivityViewModel.MainActivityViewModelFactory(application)
            )
                .get(MainActivityViewModel::class.java)
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
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
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