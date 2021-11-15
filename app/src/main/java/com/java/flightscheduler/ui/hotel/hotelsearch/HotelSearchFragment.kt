package com.java.flightscheduler.ui.hotel.hotelsearch

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.constants.AppConstants.LanguageOptions
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.databinding.FragmentHotelSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.extension.hideKeyboard
import com.java.flightscheduler.utils.extension.showListDialog
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HotelSearchFragment : BaseFragment<HotelSearchViewModel,FragmentHotelSearchBinding>(R.layout.fragment_hotel_search){
    override val viewModel: HotelSearchViewModel by viewModels()
    private var hotelSearch : HotelSearch = HotelSearch()

    override fun onBind() {
        initializeViews()
        initializeCityDropdown()
    }

    private fun initializeViews() {
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.hotelSearchViewModel = viewModel

        binding?.layoutHotelCheckInPicker?.setOnClickListener { initializeDatePicker() }
        binding?.btnFlightSearchHotels?.setOnClickListener { saveHotelResults() }
        binding?.layoutHotelLanguage?.setOnClickListener { languageDialog() }
        binding?.layoutHotelSort?.setOnClickListener{ sortDialog() }
    }

    private fun initializeCityDropdown() {
        viewModel.getCities()?.observe(viewLifecycleOwner, {
            val adapter = HotelSearchAdapter(requireContext(), it.toTypedArray())
            binding?.edtHotelSearch?.setAdapter(adapter)
        })
        binding?.edtHotelSearch?.setOnItemClickListener{ adapterView, _, i, _ ->
            val listItem = adapterView.getItemAtPosition(i)
            if (listItem is City) {
                binding?.edtHotelSearch?.setText(listItem.name)
                hotelSearch.city = listItem.code
                hotelSearch.name = listItem.name
                hotelSearch.country = listItem.country
                activity?.hideKeyboard()
            }
        }
    }

    private fun saveHotelResults() {
        hotelSearch.formattedCheckInDate = binding?.txtHotelSearchCheckInDate?.text.toString()
        hotelSearch.formattedCheckOutDate = binding?.txtHotelSearchCheckOutDate?.text.toString()
        hotelSearch.roomCount = binding?.txtHotelRoomsCount?.text.toString().toInt()
        hotelSearch.auditCount = binding?.txtHotelAuditCount?.text.toString().toInt()
        hotelSearch.sortOptions = binding?.txtHotelSort?.text.toString().toUpperCase(Locale.ENGLISH)
        hotelSearch.language = binding?.txtHotelLanguageText?.text.toString().toUpperCase(Locale.ENGLISH)

        if (paramValidation(city = hotelSearch.city)){
            viewModel.setHotelSearchLiveData(hotelSearch)
            beginTransaction(hotelSearch)
        }
    }

    private fun beginTransaction(hotelSearch: HotelSearch) {
        val action = HotelSearchFragmentDirections.actionNavHotelSearchToNavHotelResults(hotelSearch)
        findNavController().navigate(action)
    }

    private fun paramValidation(city : String): Boolean {
        var isValid = true
        viewModel.performValidation(city).observe(viewLifecycleOwner)
        { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view,errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun initializeDatePicker() {
        val intent = AirCalendarIntent(context)
        intent.setSelectButtonText(getString(R.string.text_select))
        intent.setResetBtnText(getString(R.string.text_reset))
        intent.isSingleSelect(false)
        intent.isMonthLabels(false)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN)
        startForResult.launch(intent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    initializeDateParser(it)
                }
            }
        }

    private fun initializeDateParser(intent: Intent) {
        val startDate : String = intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        val endDate : String = intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()

        hotelSearch.checkInDate = startDate
        hotelSearch.checkOutDate = endDate
        viewModel.apply { onCheckInSelected(startDate,endDate) }
    }

    private fun languageDialog() {
        showListDialog(variable = LanguageOptions.values(), cancelable = true, textView = binding?.txtHotelLanguageText)
    }

    private fun sortDialog() {
        showListDialog(variable = AppConstants.FilterOptions.values(), cancelable = true, textView = binding?.txtHotelSort)
    }
}
