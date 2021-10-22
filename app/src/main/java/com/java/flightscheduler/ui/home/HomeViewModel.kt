package com.java.flightscheduler.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.home.Places
import com.java.flightscheduler.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val locationRepository : LocationRepository) : ViewModel() {

    fun getPlaces() = MutableLiveData<Places>().apply {
        value = locationRepository.initializeMockPlaces()
    }
}
