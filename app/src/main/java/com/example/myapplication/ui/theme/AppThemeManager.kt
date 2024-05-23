package com.example.myapplication.ui.theme

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalAppTheme = compositionLocalOf<AppThemeManager> {
    error("No AppThemeManager provided")
}

class AppThemeManager(context: Context) {
    var isDarkTheme = mutableStateOf(context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)
        private set

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}