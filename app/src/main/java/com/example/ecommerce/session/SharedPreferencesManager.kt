package com.example.ecommerce.session

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SharedPreferencesManager(private val context: Context) {

    private val PREFS_NAME = "MyPrefs"
    private val IS_LOGGED_IN = "isLoggedIn"

    //here it require a context to use getSharedPreference
    // so we have to pass a context, that has been passed from the login activity

    private val sharedPreferences by lazy { context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE) }

    fun setLoggedIn() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun clearLoggedIn() {
        sharedPreferences.edit().remove(IS_LOGGED_IN).apply()
    }

}