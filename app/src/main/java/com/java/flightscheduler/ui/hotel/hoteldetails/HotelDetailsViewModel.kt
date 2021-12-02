package com.java.flightscheduler.ui.hotel.hoteldetails

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor() : BaseViewModel() {
    private val coordinatesLiveData: MutableLiveData<Pair<Double, Double>> = MutableLiveData()

    fun getCoordinatesLiveData(latitude: Double, longitude: Double): MutableLiveData<Pair<Double, Double>>? {
        /*Observable.just(Pair(latitude,longitude))
            .subscribeOn(Schedulers.io())
            .subscribe({ coordinates ->
                coordinatesLiveData.value = coordinates
            }, {
                it.printStackTrace()
            })
        */
        coordinatesLiveData.value = Pair(latitude, longitude)
        return coordinatesLiveData
    }
}
