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

    override fun getLatestCollection(): List<String> {
        val savedCollection = sharedPreferences.getStringSet(key_collection, mutableSetOf())

        return if (savedCollection.isNotEmpty()) savedCollection.toList() else mutableListOf(
            "\uD83C\uDFC0",
            "\uD83C\uDFD0",
            "\uD83C\uDFC8",
            "\uD83C\uDFC9",
            "\uD83C\uDFBE"
        )
    }


    override fun updateCollection(value: String) {

        val editor = sharedPreferences.edit()
//        同じオブジェクトの比較だと保存してくれないのでコピーする
        val latestCollection = sharedPreferences.getStringSet(key_collection, mutableSetOf()).toMutableSet()
        latestCollection.add(value)
        editor.putStringSet(key_collection, latestCollection)
        editor.apply()
    }
}