package com.java.flightscheduler.ui.base

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.java.flightscheduler.R

object MessageHelper {
    fun displayErrorMessage(view : View?, text : String){
        view?.let {
            Snackbar.make(it, text ,Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(view.context,R.color.red_google)
            ).show()
        }
    }
    fun displayInfoMessage(view : View?, text : String){
        view?.let {
            Snackbar.make(it, text ,Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(view.context,R.color.grey_800)
            ).show()
        }
    }
    fun displaySuccessMessage(view : View?, text : String){
        view?.let {
            Snackbar.make(it, text ,Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(view.context,R.color.green_google)
            ).show()
        }
    }
}