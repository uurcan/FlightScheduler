package com.java.flightscheduler.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.Places
import com.java.flightscheduler.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val textCountry = MutableLiveData<Places>().apply {
        value = Places(
            "Kuala-Lumpur",
            "Malaysia",
            ""
        )
    }
    val text: LiveData<Places> = textCountry
}
