package com.java.flightscheduler.ui.seatmap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.constants.AppConstants.MIN_AUDIT_COUNT
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.data.repository.FlightRepository
import com.java.flightscheduler.data.repository.SeatMapRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatMapViewModel @Inject constructor(private val seatMapRepository: SeatMapRepository,private val flightRepository: FlightRepository)
    : BaseViewModel() {
    private var seatMapLiveData : MutableLiveData<List<SeatMap>>? = MutableLiveData()

    fun getSeatMap(): MutableLiveData<List<SeatMap>>?{
        viewModelScope.launch {
            /*val url = flightRepository.get(
                originLocationCode = flightSearch.originLocationCode,
                destinationLocationCode = flightSearch.destinationLocationCode,
                departureDate = flightSearch.departureDate,
                returnDate = flightSearch.returnDate,
                adults = flightSearch.adults,
                children = flightSearch.children,
                max =  MIN_AUDIT_COUNT
            )*/
            //val url = seatMapRepository.getURLFromOffer(flightSearch)
            //val seatMapRequest = seatMapRepository.get(url)
            val seatMapResults = seatMapRepository.post("{\"meta\":{\"count\":1,\"links\":{\"self\":\"https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=IST&destinationLocationCode=KUL&departureDate=2021-11-11&adults=1&max=1\"}},\"data\":[{\"type\":\"flight-offer\",\"id\":\"1\",\"source\":\"GDS\",\"instantTicketingRequired\":false,\"nonHomogeneous\":false,\"oneWay\":false,\"lastTicketingDate\":\"2021-11-11\",\"numberOfBookableSeats\":4,\"itineraries\":[{\"duration\":\"PT13H55M\",\"segments\":[{\"departure\":{\"iataCode\":\"SAW\",\"at\":\"2021-11-11T13:10:00\"},\"arrival\":{\"iataCode\":\"DOH\",\"at\":\"2021-11-11T17:15:00\"},\"carrierCode\":\"QR\",\"number\":\"244\",\"aircraft\":{\"code\":\"320\"},\"operating\":{\"carrierCode\":\"QR\"},\"duration\":\"PT4H5M\",\"id\":\"1\",\"numberOfStops\":0,\"blacklistedInEU\":false},{\"departure\":{\"iataCode\":\"DOH\",\"at\":\"2021-11-11T19:40:00\"},\"arrival\":{\"iataCode\":\"KUL\",\"terminal\":\"M\",\"at\":\"2021-11-12T08:05:00\"},\"carrierCode\":\"MH\",\"number\":\"9051\",\"aircraft\":{\"code\":\"359\"},\"operating\":{\"carrierCode\":\"QR\"},\"duration\":\"PT7H25M\",\"id\":\"2\",\"numberOfStops\":0,\"blacklistedInEU\":false}]}],\"price\":{\"currency\":\"EUR\",\"total\":\"461.64\",\"base\":\"349.00\",\"fees\":[{\"amount\":\"0.00\",\"type\":\"SUPPLIER\"},{\"amount\":\"0.00\",\"type\":\"TICKETING\"}],\"grandTotal\":\"461.64\"},\"pricingOptions\":{\"fareType\":[\"PUBLISHED\"],\"includedCheckedBagsOnly\":true},\"validatingAirlineCodes\":[\"MH\"],\"travelerPricings\":[{\"travelerId\":\"1\",\"fareOption\":\"STANDARD\",\"travelerType\":\"ADULT\",\"price\":{\"currency\":\"EUR\",\"total\":\"461.64\",\"base\":\"349.00\"},\"fareDetailsBySegment\":[{\"segmentId\":\"1\",\"cabin\":\"ECONOMY\",\"fareBasis\":\"NLBCOQXX\",\"class\":\"N\",\"includedCheckedBags\":{\"weight\":25,\"weightUnit\":\"KG\"}},{\"segmentId\":\"2\",\"cabin\":\"ECONOMY\",\"fareBasis\":\"NLBCOQXX\",\"class\":\"N\",\"includedCheckedBags\":{\"weight\":25,\"weightUnit\":\"KG\"}}]}]}],\"dictionaries\":{\"locations\":{\"KUL\":{\"cityCode\":\"KUL\",\"countryCode\":\"MY\"},\"SAW\":{\"cityCode\":\"IST\",\"countryCode\":\"TR\"},\"DOH\":{\"cityCode\":\"DOH\",\"countryCode\":\"QA\"}},\"aircraft\":{\"320\":\"AIRBUS A320\",\"359\":\"AIRBUS A350-900\"},\"currencies\":{\"EUR\":\"EURO\"},\"carriers\":{\"QR\":\"QATAR AIRWAYS\",\"MH\":\"MALAYSIA AIRLINES\"}}}")
            if (seatMapResults is BaseApiResult.Success) {
                seatMapLiveData.apply {
                    seatMapLiveData?.postValue(seatMapResults.data)
                }
            }
        }
        return seatMapLiveData
    }
}