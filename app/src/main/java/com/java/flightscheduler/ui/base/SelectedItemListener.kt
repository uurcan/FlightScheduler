package com.java.flightscheduler.ui.base

import android.view.View

interface SelectedItemListener<T> {
    fun onItemClick(view: View, item: T)
}