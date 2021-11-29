package com.java.flightscheduler.ui.flight.flightresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.repository.FlightRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightResultsViewModel @Inject constructor(
    private val flightRepository: FlightRepository
) : BaseViewModel() {
    private var flightLiveData: MutableLiveData<List<FlightOffer>>? = MutableLiveData()

    init {
        showLoading()
    }

    fun getFlightData(flightSearch: FlightSearch): MutableLiveData<List<FlightOffer>>? {
        viewModelScope.launch {
            val flightOffersSearches = flightRepository.get(
                originLocationCode = flightSearch.originLocationCode,
                destinationLocationCode = flightSearch.destinationLocationCode,
                departureDate = flightSearch.departureDate,
                returnDate = flightSearch.returnDate,
                adults = flightSearch.adults,
                children = flightSearch.children
            )

            when (flightOffersSearches) {
                is BaseApiResult.Success -> {
                    flightLiveData.apply {
                        flightLiveData?.postValue(flightOffersSearches.data.toObservable()
                            .distinct { code -> code.itineraries?.get(0)?.segments?.get(0)?.number }
                            .subscribeOn(Schedulers.io())
                            .toList()
                            .blockingGet())
                    }
                    hideLoading()
                }
                is BaseApiResult.Error -> {
                    errorMessage.value = flightRepository.getQueryErrors(flightOffersSearches.errors)
                    hideLoading()
                }
            }
        }
        return flightLiveData
    }
}