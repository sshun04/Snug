package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.shojishunsuke.kibunnsns.R

class PreferenceFragment:PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference,rootKey)
    }

}