package com.shojishunsuke.kibunnsns.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.fragment.CalenderFragment
import com.shojishunsuke.kibunnsns.fragment.PreferenceFragment
import kotlinx.android.synthetic.main.fragment_record.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val toolbar = this.findViewById<Toolbar>(R.id.settingToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.pref_container, PreferenceFragment())
            .commit()


    }


//    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//
//        if (event!!.keyCode == KeyEvent.KEYCODE_BACK) {
//            finish()
//        }
//        return super.dispatchKeyEvent(event)
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId) {
//            R.id.home -> {
//                finish()
//                true
//            }
//
//            else -> false
//        }
//    }
}
