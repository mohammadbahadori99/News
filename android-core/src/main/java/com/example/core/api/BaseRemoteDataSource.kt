package com.example.core.api


import com.example.core.ApiResult
import com.example.core.Exceptions
import com.mohammad.bahadori.base.models.Resource
import retrofit2.Response

open class BaseRemoteDataSource {

    protected fun <T> checkApiResult(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                return Resource.Success(body)
        }
        val error = errorParser(response.errorBody()?.string())
        return Resource.Error(
            error
        )
    }
}
