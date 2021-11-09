package com.java.flightscheduler.ui.seatmap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.data.repository.SeatMapRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatMapViewModel @Inject constructor(private val seatMapRepository: SeatMapRepository)
    : BaseViewModel() {
    private var seatMapLiveData : MutableLiveData<List<SeatMap>>? = MutableLiveData()
    private var seatMapHeader : MutableLiveData<SeatMap>? = MutableLiveData()

    init {
        showLoading()
    }

    fun getSeatMapHeader(seatMap: SeatMap) : MutableLiveData<SeatMap>? {
        seatMapHeader.apply {
            seatMapHeader?.value = seatMap
        }
        return seatMapHeader
    }

    fun getSeatMap(): MutableLiveData<List<SeatMap>>?{
        viewModelScope.launch {
            val url = seatMapRepository.getURLFromOffer()
            val seatMapRequest = seatMapRepository.get(url)
            when (val seatMapResults = seatMapRepository.post(seatMapRequest)) {
                is BaseApiResult.Success -> {
                    seatMapLiveData.apply {
                        seatMapLiveData?.postValue(seatMapResults.data)
                    }
                    hideLoading()
                }
                is BaseApiResult.Error -> {
                    errorMessage.value = seatMapResults.errors.toString()
                    hideLoading()
                }
            }
        }
        return seatMapLiveData
    }
}