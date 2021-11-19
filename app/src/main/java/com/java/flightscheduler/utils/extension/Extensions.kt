package com.java.flightscheduler.utils.extension

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.java.flightscheduler.data.model.flight.Airport


fun FragmentActivity.hideKeyboard(){
    val inputMethodManager : InputMethodManager = getSystemService(
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

fun Fragment.airportDropdownEvent (autoCompleteTextView: AutoCompleteTextView?,
                                   keyboardHide : Boolean) : String {
    var iata = ""
    autoCompleteTextView?.setOnItemClickListener{ adapterView, _, i, _ ->
        val iataCode = adapterView.getItemAtPosition(i)
        if (iataCode is Airport) {
            val code = "${iataCode.CITY} (${iataCode.IATA})"
            autoCompleteTextView.setText(code)
            iata = iataCode.IATA.toString()

            if (keyboardHide)
                activity?.hideKeyboard()

        }
    }
    return iata
}