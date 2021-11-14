package com.java.flightscheduler.utils.extension

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData


fun hideKeyboard(fragmentActivity: FragmentActivity?){
    val inputMethodManager : InputMethodManager = fragmentActivity?.getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            fragmentActivity.currentFocus?.windowToken,
            0
        )
    }
}

fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}