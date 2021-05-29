package com.java.flightscheduler.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.Places

class HomeViewModel : ViewModel() {

    private val textCountry = MutableLiveData<Places>().apply {
        value = Places(
            "Kuala-Lumpur",
            "Malaysia",
            ""
        )
    }
    val text: LiveData<Places> = textCountry
}