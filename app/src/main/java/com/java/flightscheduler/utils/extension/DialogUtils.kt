package com.java.flightscheduler.utils.extension

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.java.flightscheduler.R
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent
import java.util.*

var loadingDialog: Dialog? = null

fun Fragment.showLoadingDialog(
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    return AlertDialog.Builder(requireContext()).apply {
        setView(R.layout.layout_loading_dialog)
    }.create().let { dialog ->
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        lifecycle.addObserver(object : LifecycleObserver {})
        loadingDialog = dialog
        dialog.show()
        dialog
    }
}

fun dismissLoadingDialog() {
    if (loadingDialog?.isShowing == true) {
        loadingDialog?.dismiss()
    }
}

var showingDialog: Dialog? = null

fun Fragment.showListDialog(
    variable: Array<*>,
    textView: TextView?,
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    return AlertDialog.Builder(context ?: return null).apply {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, variable)

        setCancelable(cancelable)
        setAdapter(adapter) { dialog, which ->
            textView?.text = variable[which].toString()
            dialog.dismiss()
        }
    }.create().let { dialog ->
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        if (showingDialog?.isShowing == true) {
            showingDialog?.dismiss()
        }
        lifecycle.addObserver(object : LifecycleObserver {})
        dialog.setOnDismissListener { showingDialog = null }
        showingDialog = dialog
        dialog.show()
        dialog
    }
}

fun Fragment.showDialog(
    title: String? = null,
    message: String? = null,
    textPositive: String? = null,
    positiveListener: (() -> Unit)? = null,
    textNegative: String? = null,
    negativeListener: (() -> Unit)? = null,
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    return AlertDialog.Builder(context ?: return null).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(textPositive) { _, _ ->
            positiveListener?.invoke()
        }
        setNegativeButton(textNegative) { _, _ ->
            negativeListener?.invoke()
        }
        setCancelable(cancelable)
    }.create().let { dialog ->
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        if (showingDialog?.isShowing == true) {
            showingDialog?.dismiss()
        }
        lifecycle.addObserver(object : LifecycleObserver {})
        dialog.setOnDismissListener { showingDialog = null }
        showingDialog = dialog
        dialog.show()
        dialog
    }
}

fun Fragment.displayTimePicker(
    context: Context? = null,
    startForResult: ActivityResultLauncher<Intent>,
    isSingleSelect: Boolean = false
) {
    val intent = AirCalendarIntent(context)
    intent.setSelectButtonText(getString(R.string.text_select))
    intent.setResetBtnText(getString(R.string.text_reset))
    intent.isSingleSelect(isSingleSelect)
    intent.isMonthLabels(false)
    intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN)
    startForResult.launch(intent)
}

fun Fragment.timePickerResult(
    textView: TextView?
) : String {
    val calendar : Calendar = Calendar.getInstance()
    val hourLocal: Int = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes: Int = calendar.get(Calendar.MINUTE)
    val timePicker = TimePickerDialog(context,
        { _, hour, minute ->
            var HH = hour
            val am_pm: String
            when { hour == 0 -> { HH += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { HH -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (textView != null) {
                val hour1 = if (hour < 10) "0$hour" else hour
                val min = if (minute < 10) "0$minute" else minute
                val msg = "$hour1 : $min $am_pm"
                textView.text = msg
                textView.visibility = ViewGroup.VISIBLE
            }
        }, hourLocal, minutes, false)
    timePicker.show()
    return "$hourLocal:$minutes:00"
}
