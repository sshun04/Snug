package com.shojishunsuke.kibunnsns.clean_arc.data

import android.content.Context
import android.content.SharedPreferences
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository

class SharedPrefRepository(context: Context) : DataConfigRepository {

    private val sharedPreferences: SharedPreferences

    private val key_collection = "KEY_COLLECTION"
    private val key_init = "KEY_INITIALIZATION"

    init {
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    override fun isInitialized(): Boolean = sharedPreferences.getBoolean(key_init, false)

    override fun updateInitializationState() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key_init, true)
        editor.apply()
    }

}