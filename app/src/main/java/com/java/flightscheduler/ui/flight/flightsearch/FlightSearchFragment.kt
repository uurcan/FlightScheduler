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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.databinding.FragmentFlightOffersBinding
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesAdapter
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesViewModel
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FlightSearchFragment : Fragment(),View.OnClickListener {
    private lateinit var binding : FragmentFlightOffersBinding
    private lateinit var navController : NavController
    private val flightSearchViewModel: FlightSearchViewModel by activityViewModels()
    private val flightRoutesViewModel : FlightRoutesViewModel by viewModels()
    private lateinit var flightSearch : FlightSearch
    private lateinit var flightOriginCity : String
    private lateinit var flightDestinationCity : String
    private lateinit var departureDate : String
    private lateinit var returnDate : String
    private var isRoundTrip : Boolean = true

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
        navController = Navigation.findNavController(view)
        initializeViews()
        initializeAirportDropdown()
    }

    private fun initializeAirportDropdown() {
        context?.let { flightRoutesViewModel.getIATACodes()?.observe(viewLifecycleOwner,{
            val adapter = FlightRoutesAdapter(requireContext(), it.toTypedArray())
            binding.edtFlightSearchOrigin.setAdapter(adapter)
            binding.edtFlightSearchDestination.setAdapter(adapter)
        })}
        binding.edtFlightSearchOrigin.setOnItemClickListener { adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is Airport) {
                binding.edtFlightSearchOrigin.setText(iataCode.IATA)
                flightOriginCity = iataCode.CITY.toString()
            }
        }
        binding.edtFlightSearchDestination.setOnItemClickListener { adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is Airport) {
                binding.edtFlightSearchDestination.setText(iataCode.IATA)
                flightDestinationCity = iataCode.CITY.toString()
            }
        }
    }

    private fun saveFlightResults() {
        val flightSearchOrigin : String = binding.edtFlightSearchOrigin.text.toString()
        val flightSearchDestination : String = binding.edtFlightSearchDestination.text.toString()
        val formattedDepartureDate : String = binding.txtFlightSearchDepartureDate.text.toString()
        val flightSearchAdultCount : Int = binding.txtFlightAdultCount.text.toString().toInt()
        val flightSearchChildrenCount : Int? = binding.txtFlightChildCount.text.toString().toIntOrNull()

        if (isRoundTrip) {
            flightSearch = FlightSearch(
                originLocationCode = flightSearchOrigin,
                destinationLocationCode = flightSearchDestination,
                originLocationCity = flightOriginCity,
                destinationLocationCity = flightDestinationCity,
                departureDate = departureDate,
                returnDate = returnDate,
                adults = flightSearchAdultCount,
                children = flightSearchChildrenCount,
                formattedDepartureDate = formattedDepartureDate,
            )
        } else {
            flightSearch = FlightSearch(
                originLocationCode = flightSearchOrigin,
                destinationLocationCode = flightSearchDestination,
                originLocationCity = flightOriginCity,
                destinationLocationCity = flightDestinationCity,
                departureDate = departureDate,
                adults = flightSearchAdultCount,
                children = flightSearchChildrenCount,
                formattedDepartureDate = formattedDepartureDate
            )
        }
        flightSearchViewModel.setFlightSearchLiveData(flightSearch)
        beginTransaction()
    }

    private fun beginTransaction() {
        navController.navigate(R.id.action_nav_flight_search_to_nav_flight_results)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { initializeDateParser(it) }
        }
    }

    private fun initializeDatePicker() {
        val intent = AirCalendarIntent(context)
        intent.setSelectButtonText(getString(R.string.text_select))
        intent.setResetBtnText(getString(R.string.text_reset))
        intent.isSingleSelect(!isRoundTrip)
        intent.isMonthLabels(false)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN)
        startForResult.launch(intent)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initializeDateParser(data: Intent) {
        val parser = SimpleDateFormat(getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(getString(R.string.text_date_formatter), Locale.ENGLISH)

        binding.txtFlightSearchDepartureDate.text = formatter.format(
            parser.parse(
                data.getStringExtra(
                    AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE,
                )
            )
        )
        departureDate = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        if (isRoundTrip) {
            binding.txtFlightSearchArrivalDate.text = formatter.format(
                parser.parse(
                    data.getStringExtra(
                        AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE
                    )
                )
            )
            returnDate =  data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btnFlightOneWay.id -> initOneWayAnimation()
            binding.btnFlightRoundTrip.id -> initRoundTripAnimation()
            binding.btnFlightSearchFlights.id -> saveFlightResults()
            binding.imgFlightAdultIncrease.id -> increaseAdultCount()
            binding.imgFlightAdultDecrease.id -> decreaseAdultCount()
            binding.imgFlightChildIncrease.id -> increaseChildrenCount()
            binding.imgFlightChildDecrease.id -> decreaseChildrenCount()
            binding.layoutFlightDeparturePicker.id -> initializeDatePicker()
            binding.layoutFlightSearchRouteSwap.id -> swapFlightRoutes()
        }
    }

    private fun decreaseChildrenCount() {
        val previousChildCount : Int? = binding.txtFlightChildCount.text.toString().toInt()
        val currentChildCount : Int? = flightRoutesViewModel.decreaseChildCount(previousChildCount)
        binding.txtFlightChildCount.text = currentChildCount.toString()
    }

    private fun increaseChildrenCount() {
        val previousChildCount : Int? = binding.txtFlightChildCount.text.toString().toInt()
        val currentChildCount : Int? = flightRoutesViewModel.increaseChildCount(previousChildCount)
        binding.txtFlightChildCount.text = currentChildCount.toString()
    }

    private fun decreaseAdultCount() {
        val previousAdultCount : Int? = binding.txtFlightAdultCount.text.toString().toInt()
        val currentAdultCount : Int? = flightRoutesViewModel.decreaseAdultCount(previousAdultCount)
        binding.txtFlightAdultCount.text = currentAdultCount.toString()
    }

    private fun increaseAdultCount() {
        val previousAdultCount : Int? = binding.txtFlightAdultCount.text.toString().toInt()
        val currentAdultCount : Int? = flightRoutesViewModel.increaseAdultCount(previousAdultCount)
        binding.txtFlightAdultCount.text = currentAdultCount.toString()
    }

    private fun swapFlightRoutes() {
        val tempOrigin : Editable? = binding.edtFlightSearchOrigin.text
        binding.edtFlightSearchOrigin.text = binding.edtFlightSearchDestination.text
        binding.edtFlightSearchDestination.text = tempOrigin
        binding.edtFlightSearchDestination.clearFocus()
    }

    private fun initializeViews() {
        binding.btnFlightOneWay.setOnClickListener(this)
        binding.btnFlightRoundTrip.setOnClickListener(this)
        binding.layoutFlightDeparturePicker.setOnClickListener(this)
        binding.btnFlightSearchFlights.setOnClickListener(this)
        binding.layoutFlightSearchRouteSwap.setOnClickListener(this)
        binding.imgFlightAdultDecrease.setOnClickListener(this)
        binding.imgFlightAdultIncrease.setOnClickListener(this)
        binding.imgFlightChildDecrease.setOnClickListener(this)
        binding.imgFlightChildIncrease.setOnClickListener(this)
    }

    private fun initRoundTripAnimation() {
        isRoundTrip = true
        TransitionManager.beginDelayedTransition(binding.layoutFlightArrivalPicker)
        binding.btnFlightRoundTrip.isSelected = true
        binding.layoutFlightDeparturePicker.layoutParams.width = 0
        binding.layoutFlightArrivalPicker.visibility = VISIBLE
    }

    private fun initOneWayAnimation() {
        isRoundTrip = false
        TransitionManager.beginDelayedTransition(binding.layoutFlightArrivalPicker)
        binding.btnFlightOneWay.isSelected = true
        binding.layoutFlightDeparturePicker.layoutParams.width = MATCH_PARENT
        binding.layoutFlightArrivalPicker.visibility = GONE
    }
}
