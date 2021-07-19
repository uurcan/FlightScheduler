package com.java.flightscheduler.ui.flight.flightsearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.IATACodes
import com.java.flightscheduler.databinding.FragmentFlightOffersBinding
import com.java.flightscheduler.ui.flight.flightresults.FlightResultsFragment
import com.yongbeom.aircalendar.AirCalendarDatePickerActivity
import com.yongbeom.aircalendar.core.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class FlightSearchFragment : Fragment(),View.OnClickListener {
    private lateinit var binding : FragmentFlightOffersBinding
    private val flightSearchViewModel: FlightSearchViewModel by activityViewModels()
    private val flightRoutesViewModel : FlightRoutesViewModel by viewModels()
    private lateinit var  flightSearch : FlightSearch
    private lateinit var departureDate : String
    private lateinit var arrivalDate : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_offers,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeAirportDropdown()
    }

    private fun initializeAirportDropdown() {
        context?.let { flightRoutesViewModel.getIATACodes()?.observe(viewLifecycleOwner,{
            val adapter = FlightRoutesAdapter(requireContext(), it.toTypedArray())
            binding.edtFlightSearchOrigin.setAdapter(adapter)
            binding.edtFlightSearchDestination.setAdapter(adapter)
        }) }
        binding.edtFlightSearchOrigin.setOnItemClickListener { adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is IATACodes)
                binding.edtFlightSearchOrigin.setText(iataCode.IATA_CODE)
        }
        binding.edtFlightSearchDestination.setOnItemClickListener { adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is IATACodes)
                binding.edtFlightSearchDestination.setText(iataCode.IATA_CODE)
        }
    }

    private fun saveFlightResults() {
        val flightSearchOrigin : String = binding.edtFlightSearchOrigin.text.toString()
        val flightSearchDestination : String = binding.edtFlightSearchDestination.text.toString()

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
        binding.txtFlightSearchDepartureDate.text = parsedDeparture
        binding.txtFlightSearchArrivalDate.text = parsedArrival
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btnFlightOneWay.id -> initOneWayAnimation()
            binding.btnFlightRoundTrip.id -> initRoundTripAnimation()
            binding.layoutFlightDeparturePicker.id -> initializeDatePicker()
            binding.btnFlightSearchFlights.id -> saveFlightResults()
            binding.layoutFlightSearchRouteSwap.id -> swapFlightRoutes()
        }
    }

    private fun swapFlightRoutes() {
        val tempOrigin : Editable? = binding.edtFlightSearchOrigin.text
        binding.edtFlightSearchOrigin.text = binding.edtFlightSearchDestination.text
        binding.edtFlightSearchDestination.text = tempOrigin
        binding.edtFlightSearchDestination.isFocusable = false
    }

    private fun initializeViews() {
        binding.btnFlightOneWay.setOnClickListener(this)
        binding.btnFlightRoundTrip.setOnClickListener(this)
        binding.layoutFlightDeparturePicker.setOnClickListener(this)
        binding.btnFlightSearchFlights.setOnClickListener(this)
        binding.layoutFlightSearchRouteSwap.setOnClickListener(this)
    }

    private fun initRoundTripAnimation() {
        TransitionManager.beginDelayedTransition(binding.layoutFlightArrivalPicker)
        binding.btnFlightRoundTrip.isSelected = true
        binding.layoutFlightDeparturePicker.layoutParams.width = 0
        binding.layoutFlightArrivalPicker.visibility = VISIBLE
    }

    private fun initOneWayAnimation() {
        TransitionManager.beginDelayedTransition(binding.layoutFlightArrivalPicker)
        binding.btnFlightOneWay.isSelected = true
        binding.layoutFlightDeparturePicker.layoutParams.width = MATCH_PARENT
        binding.layoutFlightArrivalPicker.visibility = GONE
    }
}
