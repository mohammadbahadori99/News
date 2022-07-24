package com.example.core.model

import androidx.annotation.Keep

@Keep
data class AppDetailsResponse<out T>(
    override val status: String = "",
    val data: T
) : AppResponse()
