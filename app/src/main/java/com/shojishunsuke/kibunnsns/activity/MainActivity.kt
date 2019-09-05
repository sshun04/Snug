package com.shojishunsuke.kibunnsns.activity

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseUser
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.MainActivityViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.viewmodel_factory.MainActivityViewModelFactory
import com.shojishunsuke.kibunnsns.navigation.CustomNavigator
import kotlinx.android.synthetic.main.activity_main.*

class
MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var mainViewModel: MainActivityViewModel
    private var isInitialized = false
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        mainViewModel = run {
            ViewModelProviders.of(this, MainActivityViewModelFactory(this))
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
            Log.d("Auth is Anonymous", "${currentUser.isAnonymous}")
            if (!mainViewModel.isNavigationInitialized) updateUi(currentUser)
        } else {

            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInAnonymously:success")
                        val user = auth.currentUser
                        mainViewModel.onAuthSuccess()
                        updateUi(user)
                    } else {
                        Log.d("TAG", "signInAnonymously:failure")
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun updateUi(user: FirebaseUser?) {
        Log.d("MainActivity", "updateUI")
        this.mainActivityProgressbar.visibility = View.GONE
        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        bottomNavigation = findViewById(R.id.bottom_navigation)
        val navigator =
            CustomNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.navigation)
        bottomNavigation.setupWithNavController(navController)
        mainViewModel.isNavigationInitialized = true
    }


    override fun onNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment).navigateUp()


}

