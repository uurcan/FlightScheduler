package com.java.flightscheduler.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.utils.SingleLiveEvent
import com.java.flightscheduler.utils.toBaseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel(){
    //loadingFlag
    val isLoading by lazy { MutableLiveData(false)}

    //errorMessage
    val errorMessage by lazy { SingleLiveEvent<String>() }

    //optionalFlags
    val noInternetConnectionEvent by lazy {SingleLiveEvent<Unit>()}
    val connectTimeOutEvent by lazy {SingleLiveEvent<Unit>()}
    val forceUpdateAppEvent by lazy {SingleLiveEvent<Unit>()}
    val serverMaintainErrorEvent by lazy {SingleLiveEvent<Unit>()}
    val unknownErrorEvent by lazy {SingleLiveEvent<Unit>()}

    private val exceptionHandler by lazy {
        CoroutineExceptionHandler {_,throwable ->
            viewModelScope.launch {
                onError(throwable)
            }
        }
    }

    protected open fun onError(throwable: Throwable) {
        when (throwable) {
            is UnknownHostException -> {
                noInternetConnectionEvent.call()
            }
            is ConnectException -> {
                noInternetConnectionEvent.call()
            }
            is SocketTimeoutException -> {
                connectTimeOutEvent.call()
            }
            else -> {
                val baseException = throwable.toBaseException()
                when (baseException.httpCode) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        errorMessage.value = baseException.message
                    }
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        errorMessage.value = baseException.message
                    }
                    else -> {
                        unknownErrorEvent.call()
                    }
                }
            }
        }
        hideLoading()
    }
    
    fun showLoading(){
        isLoading.value = true
    }
    fun hideLoading(){
        isLoading.value = false
    }
}