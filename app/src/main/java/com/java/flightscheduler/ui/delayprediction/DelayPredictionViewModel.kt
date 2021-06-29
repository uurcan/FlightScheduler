package com.java.flightscheduler.ui.delayprediction

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.prediction.DelayPrediction
import com.java.flightscheduler.data.repository.PredictionRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DelayPredictionViewModel @Inject constructor(
    private val predictionRepository: PredictionRepository
) : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var delayPredictionLiveData : MutableLiveData<List<DelayPrediction>> = MutableLiveData()

    fun getPredictionData() : MutableLiveData<List<DelayPrediction>>{
        scope.launch {
            val flightPredictionSearch = predictionRepository.get(
                originLocationCode = "NCE",
                destinationLocationCode = "IST",
                departureDate = "2020-08-01",
                departureTime = "18%3A20%3A00",
                arrivalDate = "2020-08-01",
                arrivalTime = "22%3A15%3A00",
                aircraftCode = "321",
                carrierCode = "TK",
                flightNumber = "1231",
                duration = "PT31H10M"
            )
            if (flightPredictionSearch is BaseApiResult.Success) {
                delayPredictionLiveData.apply {
                    delayPredictionLiveData.value = flightPredictionSearch.data
                }
            }
        }
        return delayPredictionLiveData
    }
}