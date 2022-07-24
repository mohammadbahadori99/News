package com.mohammad.bahadori.main.ui

import androidx.lifecycle.ViewModel
import com.example.core.pref.GetIsDarkThemePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getIsDarkThemePrefs: GetIsDarkThemePrefs
) : ViewModel() {

    val isDarkTheme = getIsDarkThemePrefs()
}
