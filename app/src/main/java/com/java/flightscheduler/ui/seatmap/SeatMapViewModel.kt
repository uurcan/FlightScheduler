package com.java.flightscheduler.ui.seatmap

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.data.remote.repository.SeatMapRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatMapViewModel @Inject constructor(private val seatMapRepository: SeatMapRepository) : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var seatMapLiveData : MutableLiveData<List<SeatMap>>? = MutableLiveData()

    fun getSeatMap() : MutableLiveData<List<SeatMap>>? {
        scope.launch {

            val flightDataResults = seatMapRepository.get(
                url = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=IST&destinationLocationCode=KUL&departureDate=2021-08-29&adults=1&max=1"
            )
            val seatMapResults = seatMapRepository.post(flightDataResults)
            if (seatMapResults is BaseApiResult.Success) {
                seatMapLiveData.apply {
                    seatMapLiveData?.postValue(seatMapResults.data)
                }
            }
        }
        return seatMapLiveData
    }
}