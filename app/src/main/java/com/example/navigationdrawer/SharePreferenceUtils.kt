package com.example.navigationdrawer

import android.content.Context

class SharePreferenceUtils private constructor() {
    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val KEY_LANGUAGE = "selected_language"
        private const val KEY_THEME = "selected_theme"

        // ----- LANGUAGE -----
        fun saveLanguage(context: Context, languageCode: String) {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
        }

        fun getLanguage(context: Context): String? {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return prefs.getString(KEY_LANGUAGE, null)
        }

        // ----- THEME -----
        fun saveTheme(context: Context, themeName: String) {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_THEME, themeName).apply()
        }

        fun getTheme(context: Context): String? {
            val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return prefs.getString(KEY_THEME, null)
        }
    }
}
