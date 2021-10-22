package com.java.flightscheduler.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
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
        .apply(
            RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(SIZE_ORIGINAL))
        .into(this)
}

@BindingAdapter("app:setImageDrawable")
fun ImageView.setImageDrawable(drawable : Int) {
    Glide.with(this.context)
        .load(drawable)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .apply(
            RequestOptions()
                .fitCenter()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(SIZE_ORIGINAL))
        .into(this)
}

@BindingAdapter("app:setDisplayList")
fun TextView.setDisplayList(input: List<Any>?) {
    var output = ""
    input?.forEach{ item ->
        output += item.toString().replace("_", " ") + "\n"
    }
    this.text = output
}

@BindingAdapter("app:setVisibility")
fun setVisibility(view: View, data: String?) {
    view.visibility = if (data.isNullOrBlank()) View.GONE else View.VISIBLE
}

@BindingAdapter("app:setDateParserText")
fun TextView.setDateParserText(date : String){
    val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
    val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)
    this.text =  ParsingUtils.dateParser(parser,formatter,date)
}

@BindingAdapter("app:setTimeStampParser")
fun TextView.setTimeStampParser(date : String?){
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm:ss", Locale.ENGLISH)
    this.text =  ParsingUtils.dateParser(parser,formatter,date)
}