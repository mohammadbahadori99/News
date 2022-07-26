package com.example.core.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ApiResult
import com.example.core.ExceptionHelper
import com.example.core.Exceptions
import com.example.core.R
import com.mohammad.bahadori.base.models.Resource
import com.mohammad.bahadori.base.models.getDataOrException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    protected val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO

    private val _networkViewState = MutableStateFlow(NetworkViewState())
    val networkViewState: Flow<NetworkViewState>
        get() = _networkViewState

    data class NetworkViewState(
        val showProgress: Boolean = false,
        val showProgressMore: Boolean = false,
        val showSuccess: Boolean = false,
        val showError: Boolean = false,
        val showValidationError: Boolean = false,
        val serverErrorMessage: String? = null,
        val errorMessage: Int = R.string.error_general,
        val errorIcon: Int = R.drawable.ic_general_error,
        val requestTag: String? = null,
        val validationError: Any? = null,
        val unauthorized: Boolean = false,
        val data: Any? = null
    )

    private suspend fun emitNetworkViewState(
        networkViewStates: NetworkViewState
    ) {
        withContext(Dispatchers.Main) {
            _networkViewState.emit(networkViewStates)
        }
    }

    protected suspend fun networkLoading(requestTag: String? = null) {
        emitNetworkViewState(
            NetworkViewState(
                showProgress = true,
                requestTag = requestTag
            )
        )
    }

    protected suspend fun networkMoreLoading(requestTag: String? = null) {
        emitNetworkViewState(
            NetworkViewState(
                showProgressMore = true,
                requestTag = requestTag
            )
        )
    }

    protected open suspend fun <T> observeNetworkState(
        vararg results: Resource<T>,
        requestTagList: List<String> = listOf()
    ) {
        var errorChecked = false
        var networkStateList: List<NetworkViewState> = mutableListOf()

        results.forEachIndexed { index, result ->

            if (result is Resource.Error && !errorChecked) {
                val networkViewState = getNetworkStateResult(result)
                emitNetworkViewState(networkViewState)
                errorChecked = true
            }

            // Check and get requestTag if existed
            val requestTag = requestTagList.elementAtOrNull(index)

            networkStateList = networkStateList.plus(getNetworkStateResult(result, requestTag))
        }


        // When all result become Success we have to handle them
        if (!errorChecked)
            emitNetworkViewState(
                NetworkViewState(showSuccess = true)
            )
    }

    protected open suspend fun <T> observeNetworkState(result: Resource<T>, requestTag: String) {
        emitNetworkViewState(getNetworkStateResult(result, requestTag))
    }

    private suspend fun <T> getNetworkStateResult(
        result: Resource<T>,
        requestTag: String? = null
    ): NetworkViewState {
        return when (result) {
            is Resource.Success -> {
                NetworkViewState(
                    showSuccess = true,
                    data = castData(result, requestTag),
                    requestTag = requestTag
                )
            }
            is Resource.Error -> {
                NetworkViewState(
                    showError = true,
                    serverErrorMessage = result.error.localizedMessage,
                    errorMessage = R.string.error_general,
                    unauthorized = false,
                    errorIcon = R.drawable.ic_general_error,
                    requestTag = requestTag
                )
            }
            is Resource.Loading -> {
                NetworkViewState(
                    showProgressMore = true,
                    requestTag = requestTag
                )
            }
            is Resource.Canceled -> {
                NetworkViewState(
                    showProgressMore = false,
                    requestTag = requestTag
                )
            }
            else -> {
                NetworkViewState(
                    showProgressMore = false,
                    requestTag = requestTag
                )
            }
        }
    }

    protected open suspend fun <T> castData(result: Resource<T>, requestTag: String?): Any? {
        return (result as Resource.Success).data
    }
}
