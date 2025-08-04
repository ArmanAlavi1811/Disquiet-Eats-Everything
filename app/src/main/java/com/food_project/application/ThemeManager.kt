package com.food_project.application

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val PREFS_NAME = "ThemePrefs"
    private const val KEY_THEME = "theme_mode"

    var transitionBitmap: Bitmap? = null
    var animationData: Pair<Int, Int>? = null

    enum class ThemeMode {
        LIGHT, DARK
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun applyTheme(themeMode: ThemeMode) {
        when (themeMode) {
            ThemeMode.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemeMode.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun saveTheme(context: Context, themeMode: ThemeMode) {
        getPreferences(context).edit().putString(KEY_THEME, themeMode.name).apply()
    }

    fun getSavedTheme(context: Context): ThemeMode {
        val savedTheme = getPreferences(context).getString(KEY_THEME, ThemeMode.LIGHT.name)
        return ThemeMode.valueOf(savedTheme ?: ThemeMode.LIGHT.name)
    }

    fun toggleTheme(context: Context) {
        val newTheme = if (getSavedTheme(context) == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK
        saveTheme(context, newTheme)
        applyTheme(newTheme)
    }
}