package com.java.flightscheduler.ui.hotel.hoteldetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor() : ViewModel() {
    private val coordinatesLiveData : MutableLiveData<Pair<Double,Double>>? = MutableLiveData()

    fun getCoordinatesLiveData(latitude : Double, longitude : Double) : MutableLiveData<Pair<Double,Double>>? {
        coordinatesLiveData?.value = Pair(latitude,longitude)
        return coordinatesLiveData
    }
}
