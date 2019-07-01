package com.shojishunsuke.kibunnsns.activity

import android.os.Bundle
import android.util.Log
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
import com.shojishunsuke.kibunnsns.clean_arc.presentation.MainActivityViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.MainActivityViewModelFactory
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory
import com.shojishunsuke.kibunnsns.navigation.CustomNavigator

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    lateinit var mainViewModel: MainActivityViewModel
    private var isInitialized = false
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        mainViewModel =
            ViewModelProviders.of(this, MainActivityViewModelFactory(this)).get(MainActivityViewModel::class.java)

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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {

        } else {
            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInAnonymously:success")
                        val user = auth.currentUser
//                        updateUI(user)
                    } else {
                        Log.w("TAG", "signInAnonymously:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.setupPostFragment(supportFragmentManager)
    }

    override fun onNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()


}

