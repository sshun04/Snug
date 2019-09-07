package com.shojishunsuke.kibunnsns.clean_arc.data

import android.content.Context
import android.content.SharedPreferences
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository

class SharedPrefRepository(context: Context) : DataConfigRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

    companion object{
        const val key_init = "KEY_INITIALIZATION"
    }

    override fun isInitialized(): Boolean = sharedPreferences.getBoolean(key_init, false)

    override fun updateInitializationState() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key_init, true)
        editor.apply()
    }

}