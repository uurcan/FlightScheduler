package com.java.flightscheduler.utils.extension

import android.app.Activity
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.hotel.City

fun FragmentActivity.hideKeyboard() {
    val inputMethodManager: InputMethodManager = getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            0
        )
    }
}

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun Fragment.airportDropdownEvent(
    autoCompleteTextView: AutoCompleteTextView,
    adapterView: AdapterView<*>,
    position: Int,
    keyboardHideAfter: Boolean
): Any {
    if (keyboardHideAfter)
        activity?.hideKeyboard()

    when (val data = adapterView.getItemAtPosition(position)) {
        is Airport -> {
            val code = "${data.CITY} (${data.IATA})"
            autoCompleteTextView.setText(code)
            return data
        }
        is City -> {
            autoCompleteTextView.setText(data.name)
            return data
        }
    }

    return Any()
}

fun swapRoutes(first: AutoCompleteTextView?, second: AutoCompleteTextView?) {
    val tempOrigin: Editable? = first?.text
    first?.text = second?.text
    second?.text = tempOrigin
    second?.clearFocus()
}

fun <T> merge(first: List<T>?, second: List<T>?): List<T> {
    return first!!.plus(second!!)
}