package com.java.flightscheduler.utils

import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.java.flightscheduler.data.constants.TimeConstants

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.customRefreshing(refreshing: Boolean?){
    isRefreshing = refreshing == true
}

@BindingAdapter(value = ["isLoading"])
fun ContentLoadingProgressBar.show(isLoading : Boolean?){
    if (isLoading == true)
        show()
    else
        hide()
}

@BindingAdapter("clickSafe")
fun View.setClickSafe(listener : View.OnClickListener?){
    setOnClickListener(object :  View.OnClickListener {
        var lastClickTime : Long = 0

        override fun onClick(p0: View?) {
            if (System.currentTimeMillis() - lastClickTime < TimeConstants.THRESHOLD_CLICK_TIME) return
            listener?.onClick(p0)
            lastClickTime = System.currentTimeMillis()
        }
    })
}