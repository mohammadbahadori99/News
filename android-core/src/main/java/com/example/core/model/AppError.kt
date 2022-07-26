package com.example.core.model

import androidx.annotation.Keep
import java.lang.Exception

@Keep
data class AppError(
    val status: String = "",
    override val message: String? = ""
):Exception()
