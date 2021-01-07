package com.akerimtay.movieapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.akerimtay.movieapp.App

object SharedPrefsManager {
    private const val SHARED_PREFS_NAME = "APP_PREF_NAME"
    private const val IS_DATA_INITIALIZED = "IS_DATA_CREATED"

    private var sharedPreferences: SharedPreferences =
        App.getInstance().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun save(key: String, value: Any?) {
        val editor = sharedPreferences.edit()
        when {
            value is Boolean -> editor.putBoolean(key, (value))
            value is Int -> editor.putInt(key, (value))
            value is Float -> editor.putFloat(key, (value))
            value is Long -> editor.putLong(key, (value))
            value is String -> editor.putString(key, value)
            value is Enum<*> -> editor.putString(key, value.toString())
            value != null -> throw RuntimeException("Attempting to save non-supported preference")
        }
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String): T {
        return sharedPreferences.all[key] as T
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, defValue: T): T {
        val returnValue = sharedPreferences.all[key] as T
        return returnValue ?: defValue
    }

    private fun has(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun delete(key: String) {
        if (sharedPreferences.contains(key)) {
            sharedPreferences.edit().remove(key).apply()
        }
    }

    fun isDataInitialized(): Boolean {
        return get(IS_DATA_INITIALIZED, defValue = false)
    }

    fun setDataInitialized(status: Boolean) {
        save(IS_DATA_INITIALIZED, status)
    }
}