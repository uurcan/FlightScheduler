package com.java.flightscheduler.ui.hotelsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.remote.response.FlightInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor

class HotelSearchViewModel : ViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var hotelLiveData : MutableLiveData<List<HotelOffer>>? = MutableLiveData()
    private lateinit var flightInitializer: FlightInitializer

    fun getHotelData() : MutableLiveData<List<HotelOffer>>?{
        flightInitializer = FlightInitializer()

        scope.launch {
            val hotelOfferSearches = flightInitializer.hotelSearch.get(
                cityCode = "LON",
            )

            if (hotelOfferSearches is BaseApiResult.Success) {
                hotelLiveData.apply {
                    hotelLiveData?.value = hotelOfferSearches.data
                }
            }
        }
        return hotelLiveData
    }
}