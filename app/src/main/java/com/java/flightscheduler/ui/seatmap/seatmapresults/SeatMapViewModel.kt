package com.java.flightscheduler.ui.seatmap.seatmapresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.data.model.seatmap.base.SeatMapSearch
import com.java.flightscheduler.data.repository.SeatMapRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatMapViewModel @Inject constructor(private val seatMapRepository: SeatMapRepository)
    : BaseViewModel() {
    private var seatMapLiveData : MutableLiveData<List<SeatMap>>? = MutableLiveData()
    var mapHeader : MutableLiveData<SeatMap>? = MutableLiveData()

    init {
        showLoading()
    }

    fun seatMapHeader(seatMap: SeatMap) : MutableLiveData<SeatMap>? {
        mapHeader.apply {
            mapHeader?.value = seatMap
        }
        return mapHeader
    }

    fun getSeatMapFromFlightOffer(seatMapSearch: SeatMapSearch): MutableLiveData<List<SeatMap>>?{
        viewModelScope.launch {
            val url = seatMapRepository.getURLFromOffer(seatMapSearch)
            val seatMapRequest = seatMapRepository.get(url)
            when (val seatMapResults = seatMapRepository.post(seatMapRequest)) {
                is BaseApiResult.Success -> {
                    seatMapLiveData.apply {
                        seatMapLiveData?.postValue(seatMapResults.data)
                    }
                    hideLoading()
                }
                is BaseApiResult.Error -> {
                    errorMessage.value = seatMapRepository.getQueryErrors(seatMapResults.errors)
                    hideLoading()
                }
            }
        }
        return seatMapLiveData
    }
}