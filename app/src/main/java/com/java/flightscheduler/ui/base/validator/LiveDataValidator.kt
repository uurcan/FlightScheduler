package com.java.flightscheduler.ui.base.validator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LiveDataValidator (private val liveData: LiveData<String>) {
    private val validationRules = mutableListOf<Predicate>()
    private val errorMessages = mutableListOf<String>()

    var error = MutableLiveData<String?>()

    fun isValid(): Boolean {
        for (i in 0 until validationRules.size){
            if (validationRules[i](liveData.value)){
                emitErrorMessage(errorMessages[i])
                return false
            }
        }
        emitErrorMessage(null)
        return true
    }
    private fun emitErrorMessage(messageRes : String?) {
        error.value = messageRes
    }

    fun addRule(errorMessage : String, predicate: Predicate) {
        validationRules.add(predicate)
        errorMessages.add(errorMessage)
    }
}