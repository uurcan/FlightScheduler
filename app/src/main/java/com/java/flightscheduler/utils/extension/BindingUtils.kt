package com.java.flightscheduler.utils.extension

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_AISLE
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_AVAILABLE
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_BLOCKED
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_OCCUPIED
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.prediction.DelayPrediction
import com.java.flightscheduler.ui.delayprediction.predictionsearch.DelayPredictionSearchAdapter
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchAdapter
import com.java.flightscheduler.ui.hotel.hotelsearch.HotelSearchAdapter
import com.java.flightscheduler.utils.ParsingUtils
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("app:setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.hotel_placeholder)
        .error(R.drawable.hotel_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(
            RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(SIZE_ORIGINAL)
        )
        .into(this)
}

@BindingAdapter("app:setImageDrawable")
fun ImageView.setImageDrawable(drawable: Int) {
    Glide.with(this.context)
        .load(drawable)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .apply(
            RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(SIZE_ORIGINAL)
        )
        .into(this)
}

@BindingAdapter("app:setDisplayList")
fun TextView.setDisplayList(input: List<Any>?) {
    var output = ""
    input?.forEach { item ->
        output += item.toString().replace("_", " ") + "\n"
    }
    this.text = output
}

@BindingAdapter("app:setSeatMapDrawable")
fun ImageView.setSeatMapDrawable(status: String?) {
    var drawable = 0
    when (status) {
        SEAT_MAP_AVAILABLE -> drawable = R.drawable.ic_seats_available
        SEAT_MAP_OCCUPIED -> drawable = R.drawable.ic_seats_occupied
        SEAT_MAP_BLOCKED -> drawable = R.drawable.ic_seats_blocked
        SEAT_MAP_AISLE -> drawable = R.drawable.ic_seat_aisle
    }
    val options = RequestOptions().fitCenter()

    Glide.with(this.context)
        .load(drawable)
        .apply(options)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

@BindingAdapter("app:setVisibility")
fun setVisibility(view: View, data: String?) {
    view.visibility = if (data.isNullOrBlank()) View.GONE else View.VISIBLE
}

@BindingAdapter("app:setDateParserText")
fun TextView.setDateParserText(date: String) {
    val parser = SimpleDateFormat(
        context?.getString(R.string.text_date_parser_format),
        Locale.ENGLISH
    )
    val formatter = SimpleDateFormat(
        context?.getString(R.string.text_date_formatter),
        Locale.ENGLISH
    )

    this.text = ParsingUtils.dateParser(parser, formatter, date)
}

@BindingAdapter("app:setTimeParserText")
fun TextView.setTimeParserText(time: String) {
    val parser : SimpleDateFormat = if (time.length > 10) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    } else {
        SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
    }
    val formatter = SimpleDateFormat("HH:mm aa", Locale.ENGLISH)
    this.text = ParsingUtils.dateParser(parser, formatter, time)
}

@BindingAdapter("app:setTimeStampParser")
fun TextView.setTimeStampParser(date: String?) {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm:ss", Locale.ENGLISH)
    this.text = ParsingUtils.dateParser(parser, formatter, date)
}

@BindingAdapter("app:setAutoCompleteAdapter")
fun AutoCompleteTextView.setAutoCompleteAdapter(entries: Array<Airport>) {
    val adapter = FlightSearchAdapter(context, entries)
    setAdapter(adapter)
}

@BindingAdapter("app:setAutoCompleteAdapterAirline")
fun AutoCompleteTextView.setAutoCompleteAdapterAirline(entries: Array<Airline>) {
    val adapter = DelayPredictionSearchAdapter(context, entries)
    setAdapter(adapter)
}

@BindingAdapter("app:setAutoCompleteAdapterHotel")
fun AutoCompleteTextView.setAutoCompleteAdapterHotel(entries: Array<City>) {
    val adapter = HotelSearchAdapter(context, entries)
    setAdapter(adapter)
}

@BindingAdapter("android:layout_width")
fun setLayoutWidth(view: View, width: Float) {
    val layoutParams = view.layoutParams
    layoutParams.width = width.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: View, height: Float) {
    val layoutParams = view.layoutParams
    layoutParams.height = height.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("app:setChartVariables")
fun PieChart.setChartVariables(list : List<DelayPrediction>?) {
    val entries = ArrayList<PieEntry>()
    for (i in list?.indices ?: ArrayList()) {
        val probability: Float = list?.get(i)?.probability?.toFloat()!!
        val case : String = list[i].result ?: ""
        entries.add(PieEntry(probability, case))
    }

    val pieDataSet = PieDataSet(entries, "Delay Predictions")
    pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
    pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    pieDataSet.valueTextSize = 16f

    val pieData = PieData(pieDataSet)
    this.data = pieData
}