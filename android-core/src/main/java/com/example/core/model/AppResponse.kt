package com.example.core.model

import androidx.annotation.Keep

@Keep
abstract class AppResponse {
    abstract val status: String
}
