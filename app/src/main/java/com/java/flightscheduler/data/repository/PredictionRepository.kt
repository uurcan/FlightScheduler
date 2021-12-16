package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.remote.services.PredictionService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PredictionRepository @Inject constructor(
    moshi: Moshi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val predictionService: PredictionService
) : BaseApiCall(moshi, dispatcher) {

    suspend fun get(
        originLocationCode: String,
        destinationLocationCode: String,
        departureDate: String,
        departureTime: String,
        arrivalDate: String,
        arrivalTime: String,
        aircraftCode: String,
        carrierCode: String,
        flightNumber: String,
        duration: String
    ) = baseApiCall {
        predictionService.getFlightPrediction(
            originLocationCode = originLocationCode,
            destinationLocationCode = destinationLocationCode,
            departureDate = departureDate,
            departureTime = departureTime,
            arrivalDate = arrivalDate,
            arrivalTime = arrivalTime,
            aircraftCode = aircraftCode,
            carrierCode = carrierCode,
            flightNumber = flightNumber,
            duration = duration
        )
    }

    fun getQueryErrors(errors: List<BaseApiResult.Error.Issue>): String? {
        var errorMessages = ""
        errors.forEach {
                error ->
            errorMessages += "${error.code} - ${error.title} \n"
        }
        return if (errors.isEmpty()) "No prediction result available for this flight."
        else errorMessages
    }
}