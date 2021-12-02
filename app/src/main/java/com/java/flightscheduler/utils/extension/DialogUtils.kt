package com.java.flightscheduler.utils.extension

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.java.flightscheduler.R
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent

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
