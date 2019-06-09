package com.shojishunsuke.kibunnsns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shojishunsuke.kibunnsns.data.EmtionAnalysysRepository

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rep = EmtionAnalysysRepository(this)
        val score = rep.getScore("友達が遊べなくてほんと悲しかった")
        System.out.println(score)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item2 -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }


}

