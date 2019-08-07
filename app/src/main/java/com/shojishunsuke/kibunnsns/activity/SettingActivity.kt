package com.shojishunsuke.kibunnsns.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.fragment.PreferenceFragment
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        settingToolbar.setNavigationOnClickListener {
            finish()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.pref_container, PreferenceFragment())
            .commit()
    }
}
