package com.mohammad.bahadori.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.core.collectAsStateLifecycleAware
import com.example.core.theme.ComposeNavigationTheme
import com.example.core.ui.BaseActivity
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialNavigationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val isDarkTheme by viewModel.isDarkTheme.collectAsStateLifecycleAware(initial = false)

            ComposeNavigationTheme(darkTheme = isDarkTheme) {

                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colors.background

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = statusBarColor,
                        darkIcons = !isDarkTheme
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
