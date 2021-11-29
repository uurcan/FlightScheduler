package com.java.flightscheduler.ui.flight.flightsearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.databinding.FragmentFlightSearchBinding
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesAdapter
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesViewModel
import com.java.flightscheduler.utils.ParsingUtils
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class FlightSearchFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentFlightSearchBinding
    private val flightSearchViewModel: FlightSearchViewModel by viewModels()
    private val flightRoutesViewModel: FlightRoutesViewModel by viewModels()
    private val flightSearch: FlightSearch = FlightSearch()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flight_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeFlightParams()
        initializeAirportDropdown()
    }

    private fun initializeFlightParams() {
        val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)
        val parsedCurrentDate = ParsingUtils.dateParser(
            parser = parser,
            formatter = formatter,
            date = ParsingUtils.getCurrentDate(null)
        )
        if (flightSearch.formattedDepartureDate.isBlank()) {
            if (parsedCurrentDate != null) {
                flightSearch.formattedDepartureDate = parsedCurrentDate
            }
            flightSearch.formattedReturnDate = parsedCurrentDate
        }
        binding.setVariable(BR.search, flightSearch)
    }

    private fun initializeAirportDropdown() {
        context?.let {
            flightRoutesViewModel.getIATACodes()?.observe(viewLifecycleOwner,
                {
                    val adapter = FlightRoutesAdapter(requireContext(), it.toTypedArray())
                    binding.edtFlightSearchOrigin.setAdapter(adapter)
                    binding.edtFlightSearchDestination.setAdapter(adapter)
                }
            )
        }
        binding.edtFlightSearchOrigin.setOnItemClickListener { adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is Airport) {
                val origin = "${iataCode.CITY} (${iataCode.IATA})"
                binding.edtFlightSearchOrigin.setText(origin)
                flightSearch.originLocationCode = iataCode.IATA
                flightSearch.originLocationCity = iataCode.CITY.toString()
            }
        }
        binding.edtFlightSearchDestination.setOnItemClickListener { adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is Airport) {
                val destination = "${iataCode.CITY} (${iataCode.IATA})"
                binding.edtFlightSearchDestination.setText(destination)
                flightSearch.destinationLocationCode = iataCode.IATA
                flightSearch.destinationLocationCity = iataCode.CITY.toString()
            }
        }
    }

    private fun saveFlightResults() {
        flightSearch.formattedDepartureDate = binding.txtFlightSearchDepartureDate.text.toString()
        flightSearch.formattedReturnDate = binding.txtFlightSearchArrivalDate.text.toString()
        flightSearch.adults = binding.txtFlightAdultCount.text.toString().toInt()
        flightSearch.children = binding.txtFlightChildCount.text.toString().toInt()
        flightSearch.audits = flightSearch.adults.plus(flightSearch.children)

        if (isFlightParamsValid(origin = flightSearch.originLocationCode,
                                destination = flightSearch.destinationLocationCode,
                                departureDate = flightSearch.departureDate)) {
            flightSearchViewModel.setFlightSearchLiveData(flightSearch)
            beginTransaction(flightSearch)
        }
    }

    private fun isFlightParamsValid(origin: String, destination: String, departureDate: String): Boolean {
        var isValid = true
        flightSearchViewModel.performValidation(origin, destination, departureDate).observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun beginTransaction(flightSearch: FlightSearch) {
        val action = FlightSearchFragmentDirections.actionNavFlightSearchToNavFlightResults(flightSearch)
        findNavController().navigate(action)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                initializeDateParser(it)
            }
        }
    }

    private fun initializeDatePicker() {
        val intent = AirCalendarIntent(context)
        intent.setSelectButtonText(getString(R.string.text_select))
        intent.setResetBtnText(getString(R.string.text_reset))
        intent.isSingleSelect(!flightSearch.isRoundTrip)
        intent.isMonthLabels(false)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN)
        startForResult.launch(intent)
    }

    private fun initializeDateParser(data: Intent) {
        val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)

        binding.txtFlightSearchDepartureDate.text = ParsingUtils.dateParser(
            parser = parser,
            formatter = formatter,
            date = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE)
        )
        flightSearch.departureDate = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        if (flightSearch.isRoundTrip) {
            binding.txtFlightSearchArrivalDate.text = ParsingUtils.dateParser(
                parser = parser,
                formatter = formatter,
                date = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE)
            )
            flightSearch.returnDate = data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btnFlightOneWay.id -> oneWaySelection()
            binding.btnFlightRoundTrip.id -> roundTripSelection()
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
        val previousChildCount: Int = binding.txtFlightChildCount.text.toString().toInt()
        val currentChildCount: Int? = flightRoutesViewModel.decreaseChildCount(previousChildCount)
        binding.txtFlightChildCount.text = currentChildCount.toString()
    }

    private fun increaseChildrenCount() {
        val previousChildCount: Int = binding.txtFlightChildCount.text.toString().toInt()
        val currentChildCount: Int? = flightRoutesViewModel.increaseChildCount(previousChildCount)
        binding.txtFlightChildCount.text = currentChildCount.toString()
    }

    private fun decreaseAdultCount() {
        val previousAdultCount: Int = binding.txtFlightAdultCount.text.toString().toInt()
        val currentAdultCount: Int? = flightRoutesViewModel.decreaseAdultCount(previousAdultCount)
        binding.txtFlightAdultCount.text = currentAdultCount.toString()
    }

    private fun increaseAdultCount() {
        val previousAdultCount: Int = binding.txtFlightAdultCount.text.toString().toInt()
        val currentAdultCount: Int? = flightRoutesViewModel.increaseAdultCount(previousAdultCount)
        binding.txtFlightAdultCount.text = currentAdultCount.toString()
    }

    private fun swapFlightRoutes() {
        val tempOrigin: Editable? = binding.edtFlightSearchOrigin.text
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

    private fun roundTripSelection() {
        flightSearch.isRoundTrip = true
        binding.layoutFlightArrivalPicker.visibility = VISIBLE
    }

    private fun oneWaySelection() {
        flightSearch.isRoundTrip = false
        flightSearch.returnDate = null
        binding.layoutFlightArrivalPicker.visibility = GONE
    }
}
