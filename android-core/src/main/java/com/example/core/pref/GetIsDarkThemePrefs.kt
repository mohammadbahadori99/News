package com.example.core.pref

import javax.inject.Inject

class GetIsDarkThemePrefs @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) {
    operator fun invoke() = preferencesDataStore.getIsDarkTheme
}
