package com.example.core.ui

import android.content.Context
import androidx.activity.ComponentActivity
import com.example.core.localizedContext

abstract class BaseActivity : ComponentActivity() {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(localizedContext(context))
    }

    override fun onStart() {
        super.onStart()
        localizedContext(this)
    }
}
