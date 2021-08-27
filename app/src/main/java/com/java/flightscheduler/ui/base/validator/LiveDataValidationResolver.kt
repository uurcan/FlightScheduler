package com.java.flightscheduler.ui.base.validator

class LiveDataValidationResolver (private val validators : List<LiveDataValidator>){
    fun isValid (): Boolean {
        for (validator in validators) {
            if (!validator.isValid())
                return false
        }
        return true
    }
}