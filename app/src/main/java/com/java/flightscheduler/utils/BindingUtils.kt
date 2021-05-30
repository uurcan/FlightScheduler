package com.java.flightscheduler.utils

import android.provider.SyncStateContract
import android.view.View
import androidx.databinding.BindingAdapter
import com.java.flightscheduler.data.constants.AppConstants

@BindingAdapter("clickSafe")
fun View.setClickSafe(listener : View.OnClickListener?){
    setOnClickListener(object :  View.OnClickListener {
        var lastClickTime : Long = 0

        override fun onClick(p0: View?) {
            if (System.currentTimeMillis() - lastClickTime < AppConstants.THRESHOLD_CLICK_TIME) return
            listener?.onClick(p0)
            lastClickTime = System.currentTimeMillis()
        }
    })
}