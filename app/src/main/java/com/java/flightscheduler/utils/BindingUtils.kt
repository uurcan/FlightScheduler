package com.java.flightscheduler.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.java.flightscheduler.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.hotel_placeholder)
        .error(R.drawable.hotel_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

@BindingAdapter("app:setDisplayList")
fun TextView.setDisplayList(input: List<Any>) {
    var output = ""
    input.forEach{ item ->
        output += item.toString().replace("_", " ") + "\n"
    }
    this.text = output
}

@BindingAdapter("app:setVisibility")
fun setVisibility(view: View, data: String?) {
    view.visibility = if (data.isNullOrBlank()) View.GONE else View.VISIBLE
}

@BindingAdapter("app:setDateParserTest")
fun TextView.setDateParserTest(date : String){
    val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
    val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)
    this.text =  ParsingUtils.dateParser(parser,formatter,date)
}

@BindingAdapter("app:setTimeStampParser")
fun TextView.setTimeStampParser(date : String){
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm:ss", Locale.ENGLISH)
    this.text =  ParsingUtils.dateParser(parser,formatter,date)
}