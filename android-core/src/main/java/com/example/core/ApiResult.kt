package com.example.core

import com.example.core.Exceptions

sealed class ApiResult<out T> {

    data class Success<out T>(val data: T) : ApiResult<T>()

    data class Error<out T>(val exception: Exceptions, val data: T? = null) : ApiResult<T>()
}
