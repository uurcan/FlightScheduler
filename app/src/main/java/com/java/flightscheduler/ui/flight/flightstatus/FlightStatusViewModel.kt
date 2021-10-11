package com.java.flightscheduler.ui.flight.flightstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.status.base.FlightStatus
import com.java.flightscheduler.data.repository.TokenRepository
import com.java.flightscheduler.data.repository.FlightStatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightStatusViewModel @Inject constructor(private val flightStatusRepository: FlightStatusRepository): ViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var flightStatusLiveData : MutableLiveData<List<FlightStatus>>? = MutableLiveData()
    private lateinit var tokenRepository: TokenRepository

    fun getFlightStatusLiveData() : MutableLiveData<List<FlightStatus>>?{
        tokenRepository = TokenRepository()

        scope.launch {
            val flightStatusResults = flightStatusRepository.get(
                carrierCode = "PR",
                flightNumber = 212,
                scheduledDepartureDate = "2021-06-26"
            )

            if (flightStatusResults is BaseApiResult.Success) {
                flightStatusLiveData.apply {
                    flightStatusLiveData?.postValue(flightStatusResults.data)
                }
            }
        }
        return flightStatusLiveData
    }
}