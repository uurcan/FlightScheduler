package com.java.flightscheduler.ui.hotel.hotelsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.constants.AppConstants.LanguageOptions
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.databinding.FragmentHotelSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.MessageHelper
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.extension.showListDialog
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HotelSearchFragment : BaseFragment<HotelSearchViewModel, FragmentHotelSearchBinding>(R.layout.fragment_hotel_search) {
    override val viewModel: HotelSearchViewModel by viewModels()

    override fun onBind() {
        initializeViews()
        initializeCityDropdown()
    }

    private fun initializeViews() {
        binding?.layoutHotelCheckInPicker?.setOnClickListener {
            displayTimePicker(context, startForResult, false)
        }
        binding?.btnFlightSearchHotels?.setOnClickListener {
            saveHotelResults()
        }
        binding?.layoutHotelLanguage?.setOnClickListener {
            showListDialog(variable = LanguageOptions.values(), cancelable = true, textView = binding?.txtHotelLanguageText)
        }
        binding?.layoutHotelSort?.setOnClickListener {
            showListDialog(variable = AppConstants.FilterOptions.values(), cancelable = true, textView = binding?.txtHotelSort)
        }
        binding?.hotelSearchViewModel = viewModel
    }

    private fun initializeCityDropdown() {
        binding?.edtHotelSearch?.let {
            it.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setCityLiveData(
                    airportDropdownEvent(it, adapterView, position, true) as City
                )
            }
        }
    }

    private fun saveHotelResults() {
        val hotelSearch = HotelSearch(
            city = viewModel.city.value!!,
            checkInDate = viewModel.checkInDate.value,
            checkOutDate = viewModel.checkOutDate.value,
            formattedCheckInDate = binding?.txtHotelSearchCheckInDate?.text.toString(),
            formattedCheckOutDate =  binding?.txtHotelSearchCheckOutDate?.text.toString(),
            roomCount = viewModel.roomCount.value!!,
            auditCount = binding?.txtHotelAuditCount?.text.toString().toInt(),
            sortOptions = binding?.txtHotelSort?.text.toString().uppercase(Locale.ENGLISH),
            language = binding?.txtHotelLanguageText?.text.toString().uppercase(Locale.ENGLISH)
        )

        if (paramValidation(city = hotelSearch.city.code)) {
            viewModel.setHotelSearchLiveData(hotelSearch)
            beginTransaction(hotelSearch)
        }
    }

    private fun beginTransaction(hotelSearch: HotelSearch) {
        val action = HotelSearchFragmentDirections.actionNavHotelSearchToNavHotelResults(hotelSearch)
        findNavController().navigate(action)
    }

    private fun paramValidation(city: String?): Boolean {
        var isValid = true
        viewModel.performValidation(city).observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    override fun initializeDateParser(it: Intent) {
        val startDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        val endDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()

        viewModel.apply {
            onCheckInSelected(startDate, endDate)
        }
    }
}
