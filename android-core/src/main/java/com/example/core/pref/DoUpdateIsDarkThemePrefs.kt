package com.example.core.pref

import javax.inject.Inject

class DoUpdateIsDarkThemePrefs @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) {
    suspend operator fun invoke(value: Boolean) = preferencesDataStore.updateIsDarkTheme(value)
}
