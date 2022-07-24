package com.example.core.api


import com.example.core.ApiResult
import com.example.core.Exceptions
import retrofit2.Response

open class BaseRemoteDataSource {

    protected fun <T> checkApiResult(response: Response<T>): ApiResult<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                return ApiResult.Success(body)
        }
        val error = errorParser(response.errorBody()?.string())
        return ApiResult.Error(
            Exceptions.RemoteDataSourceException(
                error.status,
                error.message,
                response.code()
            )
        )
    }
}
