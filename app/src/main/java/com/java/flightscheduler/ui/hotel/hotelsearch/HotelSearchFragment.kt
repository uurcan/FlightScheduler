package com.java.flightscheduler.ui.hotel.hotelsearch

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.LanguageOptions
import com.java.flightscheduler.data.constants.AppConstants.FilterOptions
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.databinding.FragmentHotelSearchBinding
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.ParsingUtils
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HotelSearchFragment : Fragment(), View.OnClickListener {
    private val hotelSearch : HotelSearch = HotelSearch()
    private lateinit var binding : FragmentHotelSearchBinding
    private val hotelSearchViewModel: HotelSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hotel_search,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeCityDropdown()
        initializeHotelParams()
    }

    private fun initializeHotelParams() {
        val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)

        val parsedCurrentDate : String? = ParsingUtils.dateParser(parser,formatter,ParsingUtils.getCurrentDate(null))
        if (hotelSearch.formattedCheckInDate.isNullOrBlank()){
            hotelSearch.formattedCheckInDate = parsedCurrentDate
            hotelSearch.formattedCheckOutDate = parsedCurrentDate
        }
        binding.setVariable(BR.hotelSearch,hotelSearch)
    }

    private fun initializeViews() {
        binding.imgHotelAuditDecrease.setOnClickListener(this)
        binding.imgHotelAuditIncrease.setOnClickListener(this)
        binding.imgHotelRoomsDecrease.setOnClickListener(this)
        binding.imgHotelRoomsIncrease.setOnClickListener(this)
        binding.layoutHotelLanguage.setOnClickListener(this)
        binding.layoutHotelCheckInPicker.setOnClickListener(this)
        binding.layoutHotelSort.setOnClickListener(this)
        binding.btnFlightSearchHotels.setOnClickListener(this)
    }

    private fun initializeCityDropdown() {
        hotelSearchViewModel.getCities()?.observe(viewLifecycleOwner,
            {
                val adapter = HotelSearchAdapter(requireContext(), it.toTypedArray())
                binding.edtHotelSearch.setAdapter(adapter)
            }
        )
        binding.edtHotelSearch.setOnItemClickListener{ adapterView, _, i, _ ->
            val city = adapterView.getItemAtPosition(i)
            if (city is City) {
                binding.edtHotelSearch.setText(city.name)
                hotelSearch.city = city.code
                hotelSearch.name = city.name
                hotelSearch.country = city.country
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.imgHotelAuditDecrease.id -> decreaseAuditCount()
            binding.imgHotelAuditIncrease.id -> increaseAuditCount()
            binding.imgHotelRoomsDecrease.id -> decreaseRoomCount()
            binding.imgHotelRoomsIncrease.id -> increaseRoomCount()
            binding.layoutHotelSort.id -> sortDialog()
            binding.layoutHotelLanguage.id -> languageDialog()
            binding.layoutHotelCheckInPicker.id -> initializeDatePicker()
            binding.btnFlightSearchHotels.id -> saveHotelResults()
        }
    }

    private fun saveHotelResults() {
        hotelSearch.formattedCheckInDate = binding.txtHotelSearchCheckInDate.text.toString()
        hotelSearch.formattedCheckOutDate = binding.txtHotelSearchCheckOutDate.text.toString()
        hotelSearch.roomCount = binding.txtHotelRoomsCount.text.toString().toInt()
        hotelSearch.auditCount = binding.txtHotelAuditCount.text.toString().toInt()

        if (paramValidation(city = hotelSearch.city)){
            hotelSearchViewModel.setHotelSearchLiveData(hotelSearch)
            beginTransaction(hotelSearch)
        }
    }

    private fun beginTransaction(hotelSearch: HotelSearch) {
        val action = HotelSearchFragmentDirections.actionNavHotelSearchToNavHotelResults(hotelSearch)
        findNavController().navigate(action)
    }

    private fun paramValidation(city : String): Boolean {
        var isValid = true
        hotelSearchViewModel.performValidation(city).observe(viewLifecycleOwner)
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
        val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)

        binding.txtHotelSearchCheckInDate.text = ParsingUtils.dateParser(
            parser = parser,
            formatter = formatter,
            date = intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE)
        )
        hotelSearch.checkInDate = intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        binding.txtHotelSearchCheckOutDate.text = ParsingUtils.dateParser(
            parser = parser,
            formatter = formatter,
            date = intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE)
        )
        hotelSearch.checkOutDate =  intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()
    }

    private fun decreaseAuditCount() {
        val previousCount : Int = binding.txtHotelAuditCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.decreaseAuditCount(previousCount)
        binding.txtHotelAuditCount.text = currentCount.toString()
    }

    private fun increaseAuditCount() {
        val previousCount : Int = binding.txtHotelAuditCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.increaseAuditCount(previousCount)
        binding.txtHotelAuditCount.text = currentCount.toString()
    }

    private fun decreaseRoomCount() {
        val previousCount : Int = binding.txtHotelRoomsCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.decreaseRoomCount(previousCount)
        binding.txtHotelRoomsCount.text = currentCount.toString()
    }

    private fun increaseRoomCount() {
        val previousCount : Int = binding.txtHotelRoomsCount.text.toString().toInt()
        val currentCount : Int? = hotelSearchViewModel.increaseRoomCount(previousCount)
        binding.txtHotelRoomsCount.text = currentCount.toString()
    }
    //todo: try to find more efficient way
    private fun languageDialog() {
        val builderSingle = AlertDialog.Builder(context)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            LanguageOptions.values()
        )
        builderSingle.setAdapter(
            adapter
        ) { dialog, which ->
            binding.txtHotelLanguageText.text = LanguageOptions.values()[which].name
            hotelSearch.language = LanguageOptions.values()[which].code
            dialog.dismiss()
        }
        builderSingle.show()
    }

    private fun sortDialog() {
        val builderSingle = AlertDialog.Builder(context)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            FilterOptions.values()
        )
        builderSingle.setAdapter(
            adapter
        ) { dialog, which ->
            binding.txtHotelSort.text = FilterOptions.values()[which].name
            hotelSearch.sortOptions = FilterOptions.values()[which].param
            dialog.dismiss()
        }
        builderSingle.show()
    }
}
