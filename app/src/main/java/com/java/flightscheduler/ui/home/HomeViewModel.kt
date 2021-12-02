package com.java.flightscheduler.ui.home

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.home.Places
import com.java.flightscheduler.data.repository.LocationRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val locationRepository: LocationRepository) : BaseViewModel() {

    fun getPlaces() = MutableLiveData<List<Places>>().apply {
        value = locationRepository.initializeMockPlaces()
    }
}
