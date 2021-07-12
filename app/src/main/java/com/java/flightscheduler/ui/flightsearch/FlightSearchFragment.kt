package com.java.flightscheduler.ui.flightsearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.java.flightscheduler.R
import com.yongbeom.aircalendar.AirCalendarDatePickerActivity
import com.yongbeom.aircalendar.core.AirCalendarIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_flight_offers.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class FlightSearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_flight_arrival_picker.setOnClickListener {
            initializeDatePicker()
        }
    }

    private fun initializeDatePicker() {
        val intent = AirCalendarIntent(context)
        intent.setSelectButtonText("Select")
        intent.setResetBtnText("Reset")
        intent.isBooking(false)
        intent.isSelect(false)
        intent.isMonthLabels(false)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN);
        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { initializeDateParser(it) }
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initializeDateParser(data: Intent) {
        val parser = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
        val parsedDeparture: String = formatter.format(parser.parse(data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE)))
        val parsedArrival: String = formatter.format(parser.parse(data.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE)))
        txt_flight_search_departure_date.text = parsedDeparture
        txt_flight_search_arrival_date.text = parsedArrival
    }
}
