package com.java.flightscheduler.ui.flightsearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.databinding.FragmentFlightOffersBinding
import com.yongbeom.aircalendar.AirCalendarDatePickerActivity
import com.yongbeom.aircalendar.core.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FlightSearchFragment : Fragment(),View.OnClickListener {
    private lateinit var fragmentFlightOffersBinding : FragmentFlightOffersBinding
    private val flightSearchViewModel: FlightSearchViewModel by activityViewModels()
    private lateinit var  flightSearch : FlightSearch
    private lateinit var departureDate : String
    private lateinit var arrivalDate : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFlightOffersBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_offers,container,false)
        return fragmentFlightOffersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAirportDropdown()
        fragmentFlightOffersBinding.btnFlightOneWay.setOnClickListener(this)
        fragmentFlightOffersBinding.btnFlightRoundTrip.setOnClickListener(this)
        fragmentFlightOffersBinding.layoutFlightDeparturePicker.setOnClickListener(this)
        fragmentFlightOffersBinding.btnFlightSearchFlights.setOnClickListener(this)
    }

    private fun initializeAirportDropdown() {
        val cities = arrayOf("İstanbul","İzmir")
        val adapter =
            context?.let {
                ArrayAdapter(it,android.R.layout.simple_list_item_1,cities)
            }
        fragmentFlightOffersBinding.edtFlightSearchOrigin.setAdapter(adapter)
        fragmentFlightOffersBinding.edtFlightSearchDestination.setAdapter(adapter)
    }

    private fun saveFlightResults() {
        val flightSearchOrigin : String = fragmentFlightOffersBinding.edtFlightSearchOrigin.text.toString()
        val flightSearchDestination : String = fragmentFlightOffersBinding.edtFlightSearchDestination.text.toString()

        flightSearch = FlightSearch(
            flightSearchOrigin, flightSearchDestination, departureDate, arrivalDate, 1, null, 10
        )

        flightSearchViewModel.setFlightSearchLiveData(flightSearch)
        beginTransaction()
    }

    private fun beginTransaction() {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            add<FlightResultsFragment>(R.id.nav_host_fragment,null)
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { initializeDateParser(it) }
        }
    }

    private fun initializeDatePicker() {
        val intent = AirCalendarIntent(context)
        intent.setSelectButtonText("Select")
        intent.setResetBtnText("Reset")
        intent.isBooking(false)
        intent.isSingleSelect(true)
        intent.isMonthLabels(false)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN)
        startForResult.launch(intent)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initializeDateParser(data: Intent) {
        val parser = SimpleDateFormat(getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(getString(R.string.text_date_formatter), Locale.ENGLISH)
        val parsedDeparture: String = formatter.format(
            parser.parse(
                data.getStringExtra(
                    AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE,
                )
            )
        )
        val parsedArrival: String = formatter.format(
            parser.parse(
                data.getStringExtra(
                    AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE
                )
            )
        )

        departureDate = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        arrivalDate =  data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()
        fragmentFlightOffersBinding.txtFlightSearchDepartureDate.text = parsedDeparture
        fragmentFlightOffersBinding.txtFlightSearchArrivalDate.text = parsedArrival
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            fragmentFlightOffersBinding.btnFlightOneWay.id -> initOneWayAnimation()
            fragmentFlightOffersBinding.btnFlightRoundTrip.id -> initRoundTripAnimation()
            fragmentFlightOffersBinding.layoutFlightDeparturePicker.id -> initializeDatePicker()
            fragmentFlightOffersBinding.btnFlightSearchFlights.id -> saveFlightResults()
        }
    }

    private fun initRoundTripAnimation() {
        fragmentFlightOffersBinding.btnFlightRoundTrip.isSelected = true
        TransitionManager.beginDelayedTransition(fragmentFlightOffersBinding.layoutFlightArrivalPicker)
        fragmentFlightOffersBinding.layoutFlightDeparturePicker.layoutParams.width = 0
        fragmentFlightOffersBinding.layoutFlightArrivalPicker.visibility = VISIBLE
    }

    private fun initOneWayAnimation() {
        fragmentFlightOffersBinding.btnFlightOneWay.isSelected = true
        TransitionManager.beginDelayedTransition(fragmentFlightOffersBinding.layoutFlightArrivalPicker)
        fragmentFlightOffersBinding.layoutFlightDeparturePicker.layoutParams.width = MATCH_PARENT
        fragmentFlightOffersBinding.layoutFlightArrivalPicker.visibility = GONE
    }
}
