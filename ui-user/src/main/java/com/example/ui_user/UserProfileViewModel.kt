package com.example.ui_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.pref.DoUpdateIsDarkThemePrefs
import com.example.core.pref.GetIsDarkThemePrefs
import com.example.domain_user.usecase.GetUserProfileLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    getUserProfileLocal: GetUserProfileLocalUseCase,
    private val getIsDarkThemePrefs: GetIsDarkThemePrefs,
    private val doUpdateIsDarkThemePrefs: DoUpdateIsDarkThemePrefs
) : ViewModel() {

    val userProfile = getUserProfileLocal()

    val isDarkTheme = getIsDarkThemePrefs()

    fun changeTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            val isDarkTheme = getIsDarkThemePrefs.invoke().first()
            doUpdateIsDarkThemePrefs(!isDarkTheme)
        }
    }
}
