package com.java.flightscheduler.ui.delayprediction.predictionresults

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.prediction.DelayPrediction
import com.java.flightscheduler.data.model.prediction.PredictionSearch
import com.java.flightscheduler.data.repository.PredictionRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DelayPredictionViewModel @Inject constructor(
    private val predictionRepository: PredictionRepository
) : BaseViewModel() {
    private var delayPredictionLiveData: MutableLiveData<List<DelayPrediction>>? = MutableLiveData()
    val delayPrediction : LiveData<List<DelayPrediction>>? get() = delayPredictionLiveData

    init {
        showLoading()
    }

    fun setDelayPrediction(prediction : List<DelayPrediction>) {
        delayPredictionLiveData?.value = prediction
    }

    fun getPredictionData(predictionSearch: PredictionSearch): MutableLiveData<List<DelayPrediction>>? {
        viewModelScope.launch {
            val flightPredictionSearch = predictionRepository.get(
                originLocationCode = predictionSearch.origin?.IATA.toString(),
                destinationLocationCode = predictionSearch.destination?.IATA.toString(),
                departureDate = predictionSearch.departureDate.toString(),
                departureTime = predictionSearch.departureTime.toString(),
                arrivalDate = predictionSearch.departureDate.toString(),
                arrivalTime = predictionSearch.arrivalTime.toString(),
                aircraftCode = predictionSearch.aircraftCode.toString(),
                carrierCode = predictionSearch.carrierCode?.ID.toString(),
                flightNumber = predictionSearch.flightNumber.toString(),
                duration = predictionSearch.duration
            )
            when (flightPredictionSearch) {
                is BaseApiResult.Success -> {
                    delayPredictionLiveData.apply {
                        delayPredictionLiveData?.postValue(flightPredictionSearch.data)
                        hideLoading()
                    }
                }
                is BaseApiResult.Error -> {
                    errorMessage.value = predictionRepository.getQueryErrors(flightPredictionSearch.errors)
                    hideLoading()
                }
            }
        }
        return delayPredictionLiveData
    }
}